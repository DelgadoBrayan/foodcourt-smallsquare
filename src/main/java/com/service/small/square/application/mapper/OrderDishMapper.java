package com.service.small.square.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



import com.service.small.square.application.dto.order.OrderDishDto;
import com.service.small.square.domain.model.order.OrderDish;

@Mapper(componentModel = "spring", uses = {DishMapper.class})
public interface OrderDishMapper {

    @Mapping(source = "dishId", target = "dishId")
    OrderDish toDomain(OrderDishDto orderDishDto);

    OrderDishDto toDto(OrderDish orderDish);

    List<OrderDish> toDomain(List<OrderDishDto> orderDishDtos);

    List<OrderDishDto> toDto(List<OrderDish> orderDishes);
}
