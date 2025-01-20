package com.service.small.square.domain.usecase;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.service.small.square.domain.api.IOrderServicePort;
import com.service.small.square.domain.messages.OrderMessages;
import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.domain.model.order.OrderDishList;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.domain.model.order.efficiency.EmployeeEfficiency;
import com.service.small.square.domain.model.order.efficiency.OrderEfficiency;
import com.service.small.square.domain.service.EmployeeValidationService;
import com.service.small.square.domain.service.TrackingService;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;
import com.service.small.square.domain.spi.IOrderPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidOrderException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final EmployeeValidationService employeeValidationService;
    private final TrackingService trackingService;


    @Override
    public Order createOrder(Order order, List<Long> listDish) {
        if (orderPersistencePort.existsByClientIdAndStatus(order.getClientId(),
                List.of("PENDING", "IN_PROCESS", "READY"))) {
            throw new InvalidOrderException(OrderMessages.ORDER_ALREADY_ACTIVE);
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

        trackingService.postTrackingInfo(savedOrder.getClientId(), savedOrder.getId(),
                savedOrder.getStatus().toString());
        return savedOrder;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderPersistencePort.findById(id)
                .orElseThrow(() -> new InvalidOrderException(OrderMessages.ORDER_NOT_FOUND + id));
    }

    @Override
    public List<OrderDishList> getOrdersByStatus(String status, Long restaurantId, int page, int size) {
        if (restaurantId == null) {
            throw new InvalidOrderException(OrderMessages.RESTAURANT_ID_ISREQUIRED);
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
        employeeValidationService.validateEmployee(employeeId, restaurantId, token);
        if (!order.getRestaurantId().equals(restaurantId)) {
            throw new InvalidOrderException(OrderMessages.EMPLOYEE_NOT_BELONG_TO_RESTAURANT);
        }

        order.setChefId(employeeId);
        order.setStatus(OrderStatus.IN_PROCESS);
        trackingService.patchTrackingInfo(orderId, OrderStatus.IN_PROCESS.toString(), OrderMessages.ORDER_IN_PROCESS);
        orderPersistencePort.assignEmployeeToOrder(orderId, employeeId);
    }

    @Override
    public void noticationOrderReady(Long orderId, String token) {
        Order order = getOrderById(orderId);
        updateOrderStatus(order, token);

        trackingService.patchTrackingInfo(order.getId(), OrderStatus.READY.toString(),
                OrderMessages.ORDER_READY_NOTIFICATION);

        orderPersistencePort.noticationOrderReady(orderId, token);
    }

    private void updateOrderStatus(Order order, String token) {
        if (OrderStatus.IN_PROCESS != order.getStatus()) {
            throw new InvalidOrderException(OrderMessages.ORDER_CANNOT_BE_UPDATED);
        }

        employeeValidationService.validateEmployee(order.getChefId(), order.getRestaurantId(), token);

        order.setStatus(OrderStatus.READY);
        Order updatedOrder = orderPersistencePort.save(order);
        if (OrderStatus.READY != updatedOrder.getStatus()) {
            throw new InvalidOrderException(OrderMessages.ORDER_CANNOT_BE_DELIVERED);
        }
    }

    @Override
    public void deliverOrder(Long orderId, String pin, String token) {
        Order order = getOrderById(orderId);
        employeeValidationService.validateEmployee(order.getChefId(), order.getRestaurantId(), token);
        if (order.getStatus() != OrderStatus.READY) {
            throw new InvalidOrderException(OrderMessages.ORDER_CANNOT_BE_DELIVERED);
        }
        if (!order.getPin().equals(pin)) {
            throw new InvalidOrderException(OrderMessages.PIN_DOES_NOT_MATCH);
        }
        trackingService.patchTrackingInfo(orderId,OrderStatus.DELIVERED.toString(),OrderMessages.ORDER_DELIVERED_NOTIFICATION);
        order.setStatus(OrderStatus.DELIVERED);
        order.setOrderFinished(LocalDateTime.now());
        orderPersistencePort.save(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.CANCELED);
            trackingService.patchTrackingInfo(orderId, OrderStatus.CANCELED.toString(),
                    OrderMessages.ORDER_CANCELED_NOTIFICATION);
            orderPersistencePort.save(order);
        } else {
            throw new InvalidOrderException(OrderMessages.ORDER_CANCEL_NOT_ALLOWED);
        }
    }

     @Override
    public List<OrderEfficiency> calculateOrderTimes() {
        return orderPersistencePort.findAllOrders().stream()
            .filter(order -> order.getOrderFinished() != null)
            .map(order -> new OrderEfficiency(
                order.getId(),
                Duration.between(order.getDate(), order.getOrderFinished()).toMinutes()
            ))
            .toList();
    }

    @Override
    public List<EmployeeEfficiency> calculateAverageTimeByEmployee() {
        Map<Long, List<Order>> ordersByChef = orderPersistencePort.findAllOrders().stream()
            .filter(order -> order.getOrderFinished() != null)
            .collect(Collectors.groupingBy(Order::getChefId));

        return ordersByChef.entrySet().stream()
            .map(entry -> new EmployeeEfficiency(
                entry.getKey(),
                entry.getValue().stream()
                    .mapToLong(order -> Duration.between(order.getDate(), order.getOrderFinished()).toMinutes())
                    .average()
                    .orElse(0)
            ))
            .toList();
    }

}
