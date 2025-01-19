package com.service.small.square.domain.usecase;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.domain.api.IOrderServicePort;
import com.service.small.square.domain.model.EmployeeRestaurant;
import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.domain.model.order.OrderDishList;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;
import com.service.small.square.domain.spi.IOrderPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidOrderException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final WebClient employeRestaurantWebClient;

    @Override
    public Order createOrder(Order order, List<Long> listDish) {
        if (orderPersistencePort.existsByClientIdAndStatus(order.getClientId(), List.of("PENDING", "IN_PROCESS", "READY"))) {
            throw new InvalidOrderException("Cliente ya tiene una orden activa");
        }

        order.setStatus(OrderStatus.PENDING);
        order.setDate(LocalDateTime.now());
        Order savedOrder = orderPersistencePort.save(order);
        Map<Long, Integer> dishCountMap = new HashMap<>();
        for (Long dishId : listDish) {
            dishCountMap.put(dishId, dishCountMap.getOrDefault(dishId, 0) + 1);
        }

        dishCountMap.forEach((dishId, quantity) -> {
            OrderDish orderDish = new OrderDish();
            orderDish.setOrderId(savedOrder.getId());
            orderDish.setDishId(dishId);
            orderDish.setQuantity(quantity);
            orderDishPersistencePort.saveOrderDish(orderDish);
        });

        return savedOrder;
    }


    @Override
    public Order getOrderById(Long id) {
        return orderPersistencePort.findById(id).orElseThrow(() -> new InvalidOrderException("Orden no encontrada" + id));
    }

    @Override
    public List<OrderDishList> getOrdersByStatus(String status, Long restaurantId, int page, int size) {
        if (restaurantId == null) {
            throw new InvalidOrderException("El ID del restaurante es requerido");
        }
        return orderPersistencePort.findOrdersByStatusAndRestaurantId(status, restaurantId, page, size);
    }

    @Override
    public boolean existsByClientIdAndStatus(Long clientId, List<String> statuses) {
        return orderPersistencePort.existsByClientIdAndStatus(clientId, statuses);
    }

    @Override
    public void assignEmployeeToOrder(Long orderId, Long employeeId, Long restaurantId, String token) {
       
        Order order = getOrderById(orderId);
        validateEmployeeRestaurant(employeeId, restaurantId, token);
        if (!order.getRestaurantId().equals(restaurantId)) {
            throw new InvalidOrderException("El empleado no pertenece a este restaurante.");
        }

        order.setChefId(employeeId);
        order.setStatus(OrderStatus.IN_PROCESS);
        orderPersistencePort.assignEmployeeToOrder(orderId, employeeId);
    }
    @Override
    public void noticationOrderReady(Long orderId, String token) {
        Order order = getOrderById(orderId);
        updateOrderStatus(order, token);
    

        orderPersistencePort.noticationOrderReady(orderId, token);
    }
    
    
    private void updateOrderStatus(Order order, String token) {
        if (OrderStatus.IN_PROCESS != order.getStatus()) {
            throw new InvalidOrderException("Esta orden no se puede actualizar a listo.");
        }

        validateEmployeeRestaurant(order.getChefId(), order.getRestaurantId(), token);
    
        order.setStatus(OrderStatus.READY);
        Order updatedOrder = orderPersistencePort.save(order);
    
        if (OrderStatus.READY != updatedOrder.getStatus()) {
            throw new InvalidOrderException("La orden no está en estado listo.");
        }
    }


    @Override
    public void deliverOrder(Long orderId, String pin, String token) {
        Order order = getOrderById(orderId);
        validateEmployeeRestaurant(order.getChefId(),order.getRestaurantId(), token);
        if(order.getStatus() != OrderStatus.READY){
            throw new InvalidOrderException("La orden no puede ser entregada, el estado no corresponde");
        }
        if(!order.getPin().equals(pin)){
            throw new InvalidOrderException("El pin no corresponde a la orden");
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setOrderFinished(LocalDateTime.now());
        orderPersistencePort.save(order);
    }


    @Override
    public void cancelOrder(Long orderId) {
       Order order = getOrderById(orderId);
       if(order.getStatus() == OrderStatus.PENDING){
        order.setStatus(OrderStatus.CANCELED);
        orderPersistencePort.save(order);
       }else{
        throw new InvalidOrderException("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");
       }
    }

    public boolean validateEmployeeRestaurant(Long employeeId, Long restaurantId, String token){
        try {
            EmployeeRestaurant employeeRestaurant = employeRestaurantWebClient.get()
            .uri("/{employeeId}", employeeId)
            .headers(headers -> headers.setBearerAuth(token))
            .retrieve()
            .onStatus(status -> status == HttpStatus.BAD_REQUEST, clientResponse -> Mono.error(new InvalidOrderException("Error al obtener la consulta")))
            .bodyToMono(EmployeeRestaurant.class)
            .block();

        return employeeId.equals(employeeRestaurant.getEmplooyeId()) && restaurantId.equals(employeeRestaurant.getRestaurantId());
        } catch (InvalidOrderException e) {
           throw new InvalidOrderException("No perteneces a este restaurante, no puedes realizar esta accion");
        }
    }
}
