package com.service.small.square.infrastucture.out.jpa.adapter;

import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;
import com.service.small.square.infrastucture.out.jpa.entity.OrderDishEntity;
import com.service.small.square.infrastucture.out.jpa.mapper.OrderDishEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.OrderDishRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderDishAdapter implements IOrderDishPersistencePort {

    private final OrderDishRepository orderDishRepository;
    private final OrderDishEntityMapper orderDishEntityMapper;
    @Override
    public OrderDish saveOrderDish(OrderDish orderDish) {
       OrderDishEntity orderDishEntity = orderDishEntityMapper.toEntity(orderDish);
       return orderDishEntityMapper.toDomain(orderDishRepository.save(orderDishEntity));
     
    }
    
}
