package com.service.small.square.infrastucture.out.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.small.square.infrastucture.out.jpa.entity.RestaurantEntity;


public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Long> {
}
