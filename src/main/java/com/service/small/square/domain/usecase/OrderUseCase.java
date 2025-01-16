package com.service.small.square.domain.usecase;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.service.small.square.domain.api.IOrderServicePort;
import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;
import com.service.small.square.domain.spi.IOrderPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;

    @Override
    public Order createOrder(Order order, List<Long> listDish) {
        if (orderPersistencePort.existsByClientIdAndStatus(order.getClientId(), List.of("PENDING", "IN_PROCESS"))) {
            throw new IllegalArgumentException("Client already has an active order.");
        }

        System.out.println(order);
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
        return orderPersistencePort.findById(id).orElseThrow(() -> new RuntimeException("Order not found."));
    }

    @Override
    public List<Order> getOrdersByStatus(String status, Long restaurantId, int page, int size) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or empty.");
        }
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null.");
        }
        return orderPersistencePort.findByStatus(status, restaurantId, page, size);
    }

    @Override
    public boolean existsByClientIdAndStatus(Long clientId, List<String> statuses) {
        return orderPersistencePort.existsByClientIdAndStatus(clientId, statuses);
    }
}
