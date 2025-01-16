package com.service.small.square.infrastucture.out.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.small.square.infrastucture.out.jpa.entity.OrderDishEntity;

public interface OrderDishRepository extends JpaRepository<OrderDishEntity, Long>{
    
}
