package com.service.small.square.infrastucture.out.jpa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.infrastucture.out.jpa.entity.OrderDishEntity;
import com.service.small.square.infrastucture.out.jpa.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity orderEntity);

    List<OrderEntity> toEntityList(List<Order> orders);

    List<Order> toDomainList(List<OrderEntity> orderEntities);

    @Mapping(source = "orderId", target = "order.id")
    OrderDishEntity toEntity(OrderDish orderDish);

    @Mapping(source = "order.id", target = "orderId")
    OrderDish toDomain(OrderDishEntity orderDishEntity);

    List<OrderDishEntity> toEntityListDishes(List<OrderDish> orderDishes);

    List<OrderDish> toDomainListDishes(List<OrderDishEntity> orderDishEntities);
}
