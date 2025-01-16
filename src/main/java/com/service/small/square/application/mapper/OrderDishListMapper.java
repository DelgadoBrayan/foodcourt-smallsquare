package com.service.small.square.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.service.small.square.application.dto.dish.DishDto;
import com.service.small.square.application.dto.order.OrderDishListDto;
import com.service.small.square.domain.model.dish.Dish;
import com.service.small.square.domain.model.order.OrderDishList;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.infrastucture.out.jpa.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderDishListMapper {

    @Mapping(target = "status", source = "status", qualifiedByName ="mapOrderStatusToString" ) // Convierte enum a String
    OrderDishListDto toDTO(OrderDishList order);

    List<DishDto> toDishDTOList(List<Dish> dishes);

    @Mapping(source = "dishInfo.name", target = "name")
    @Mapping(source = "dishInfo.price", target = "price")
    @Mapping(source = "dishInfo.description", target = "description")
    @Mapping(source = "dishInfo.urlImage", target = "urlImage")
    @Mapping(source = "dishInfo.category", target = "category")
    DishDto toDishDTO(Dish dish);

        @Named("mapOrderStatusToString")
    default String mapOrderStatusToString(OrderStatus status) {
        return status != null ? status.toString() : null;
    }

    @Mapping(target = "listDishes", ignore = true) // Ignorar listDishes en el mapeo inicial
    OrderDishList toDomain(OrderEntity orderEntity);
}
