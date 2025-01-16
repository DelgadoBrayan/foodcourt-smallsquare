package com.service.small.square.infrastucture.out.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.infrastucture.out.jpa.entity.OrderDishEntity;

@Mapper(componentModel = "spring")
public interface OrderDishEntityMapper {
    @Mapping(source = "orderId", target = "order.id")
    OrderDishEntity toEntity(OrderDish orderDish);

    @Mapping(source = "order.id", target = "orderId")
    OrderDish toDomain(OrderDishEntity orderDishEntity);
}
