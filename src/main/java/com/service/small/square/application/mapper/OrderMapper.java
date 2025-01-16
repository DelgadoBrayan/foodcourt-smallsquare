package com.service.small.square.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.small.square.application.dto.order.OrderDto;
import com.service.small.square.domain.model.order.Order;

@Mapper(componentModel = "spring", uses = {OrderDishMapper.class})
public interface OrderMapper {

    Order toDomain(OrderDto orderDto);

    @Mapping(target = "listDishes" , ignore = true)
    OrderDto toDto(Order order);
}
