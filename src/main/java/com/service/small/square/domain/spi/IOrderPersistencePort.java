package com.service.small.square.domain.spi;

import java.util.List;
import java.util.Optional;

import com.service.small.square.domain.model.order.Order;

public interface IOrderPersistencePort {
    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByStatus(String status, Long restaurantId, int page, int size);

    boolean existsByClientIdAndStatus(Long clientId, List<String> statuses);

}
