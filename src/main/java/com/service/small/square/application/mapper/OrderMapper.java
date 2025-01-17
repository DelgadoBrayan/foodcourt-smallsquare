package com.service.small.square.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.small.square.application.dto.order.OrderDto;
import com.service.small.square.application.dto.order.OrderRequest;
import com.service.small.square.domain.model.order.Order;

@Mapper(componentModel = "spring", uses = {OrderDishMapper.class})
public interface OrderMapper {

    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "pin", ignore = true)
    @Mapping(target = "orderFinished", ignore = true)
    @Mapping(target = "chefId", ignore = true)
    Order toSave(OrderRequest orderRequest);
    @Mapping(target = "listDishes" , ignore = true)
    OrderRequest toDtoSave(Order order);

    Order toDomain(OrderDto orderDto);

    @Mapping(target = "listDishes" , ignore = true)
    OrderDto toDto(Order order);
}
