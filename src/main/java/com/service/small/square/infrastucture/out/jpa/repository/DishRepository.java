package com.service.small.square.infrastucture.out.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.small.square.infrastucture.out.jpa.entity.DishEntity;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {

}
