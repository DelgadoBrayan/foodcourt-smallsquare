package com.service.small.square.domain.usecase;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.service.small.square.domain.api.IOrderServicePort;
import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.domain.model.order.OrderDishList;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;
import com.service.small.square.domain.spi.IOrderPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidOrderException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;

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
        return orderPersistencePort.findById(id).orElseThrow(() -> new InvalidOrderException("Order not found." + id));
    }

    @Override
    public List<OrderDishList> getOrdersByStatus(String status, Long restaurantId, int page, int size) {
        //Pendiente hacer la validacion de que el empleado puede listar las ordenes solamente del restaurante al que pertenece
        if (restaurantId == null) {
            throw new InvalidOrderException("Restaurant ID is required and must be greater than 0.");
        }
        if (status == null || status.isBlank()) {
            throw new InvalidOrderException("Status is required.");
        }
        return orderPersistencePort.findOrdersByStatusAndRestaurantId(status, restaurantId, page, size);
    }

    @Override
    public boolean existsByClientIdAndStatus(Long clientId, List<String> statuses) {
        return orderPersistencePort.existsByClientIdAndStatus(clientId, statuses);
    }

    @Override
    public void assignEmployeeToOrder(Long orderId, Long employeeId, Long restaurantId) {
        //Pendiente hacer la validacion de que el empleado puede listar las ordenes solamente del restaurante al que pertenece
        Order order = getOrderById(orderId);

        if (!order.getRestaurantId().equals(restaurantId)) {
            throw new InvalidOrderException("Employee does not belong to this restaurant");
        }

        order.setChefId(employeeId);
        order.setStatus(OrderStatus.IN_PROCESS);
        orderPersistencePort.assignEmployeeToOrder(orderId, employeeId);
    }
    @Override
    public void noticationOrderReady(Long orderId, String token) {
        Order order = getOrderById(orderId);
        updateOrderStatus(order);
    

        orderPersistencePort.noticationOrderReady(orderId, token);
    }
    
    
    private void updateOrderStatus(Order order) {
        if (OrderStatus.IN_PROCESS != order.getStatus()) {
            throw new InvalidOrderException("Esta orden no se puede actualizar a listo.");
        }
    
        order.setStatus(OrderStatus.READY);
        Order updatedOrder = orderPersistencePort.save(order);
    
        if (OrderStatus.READY != updatedOrder.getStatus()) {
            throw new InvalidOrderException("La orden no está en estado listo.");
        }
    }


    @Override
    public void deliverOrder(Long orderId, String pin) {
        Order order = getOrderById(orderId);
        if(order.getStatus() != OrderStatus.READY){
            throw new InvalidOrderException("La orden no puede ser entregada, el estado no corresponde");
        }
        if(!order.getPin().equals(pin)){
            throw new InvalidOrderException("El pin no corresponde a la orden");
        }

        order.setStatus(OrderStatus.DELIVERED);
        orderPersistencePort.save(order);
    }
    
}
