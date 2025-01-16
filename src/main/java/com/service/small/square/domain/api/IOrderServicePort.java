package com.service.small.square.domain.api;

import java.util.List;

import com.service.small.square.domain.model.order.Order;

public interface IOrderServicePort {
    Order createOrder(Order order,  List<Long> listDishes);

    Order getOrderById(Long id);

    List<Order> getOrdersByStatus(String status, Long restaurantId, int page, int size);

    boolean existsByClientIdAndStatus(Long clientId, List<String> statuses);
}
