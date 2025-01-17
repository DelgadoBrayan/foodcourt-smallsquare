package com.service.small.square.domain.spi;

import java.util.List;
import java.util.Optional;

import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDishList;

public interface IOrderPersistencePort {
    Order save(Order order);

    Optional<Order> findById(Long id);

    List<OrderDishList> findOrdersByStatusAndRestaurantId(String status, Long restaurantId, int page, int size);

    boolean existsByClientIdAndStatus(Long clientId, List<String> statuses);

    void assignEmployeeToOrder(Long orderId, Long employeeId);

    void noticationOrderReady(Long orderId, String token);

    void deliverOrder(Long orderId);

    void cancelOrder(Long orderId);
}
