package com.service.small.square.domain.api;

import java.util.List;

import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDishList;

public interface IOrderServicePort {
    Order createOrder(Order order,  List<Long> listDishes);

    Order getOrderById(Long id);

    List<OrderDishList> getOrdersByStatus(String status, Long restaurantId, int page, int size);

    boolean existsByClientIdAndStatus(Long clientId, List<String> statuses);

    void assignEmployeeToOrder(Long orderId, Long employeeId, Long restaurantId); 

    void noticationOrderReady(Long orderId, String token);

    void deliverOrder(Long orderId, String pin);

    void cancelOrder(Long orderId);
}
