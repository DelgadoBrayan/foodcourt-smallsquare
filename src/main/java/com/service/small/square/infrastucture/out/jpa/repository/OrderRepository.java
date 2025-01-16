package com.service.small.square.infrastucture.out.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.infrastucture.out.jpa.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM OrderEntity o WHERE o.clientId = :clientId AND o.status IN :statuses")
    boolean existsByClientIdAndStatus(@Param("clientId") Long clientId, @Param("statuses") List<OrderStatus> statuses);

    Optional<OrderEntity> findById(Long id);

    @Query("SELECT o FROM OrderEntity o WHERE o.status = :status AND o.restaurantId = :restaurantId")
    List<OrderEntity> findByStatusAndRestaurantId(@Param("status") OrderStatus status,
            @Param("restaurantId") Long restaurantId, Pageable pageable);
}