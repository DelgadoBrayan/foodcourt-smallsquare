package com.service.small.square.infrastucture.out.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.small.square.domain.model.dish.Dish;
import com.service.small.square.infrastucture.out.jpa.entity.DishEntity;



@Mapper(componentModel = "spring")
public interface DishEntityMapper {

    @Mapping(source = "dishInfo.name", target = "name")
    @Mapping(source = "dishInfo.price", target = "price")
    @Mapping(source = "dishInfo.description", target = "description")
    @Mapping(source = "dishInfo.urlImage", target = "urlImage")
    @Mapping(source = "dishInfo.category", target = "category")
    DishEntity toEntity(Dish dish);

    @Mapping(source = "name", target = "dishInfo.name")
    @Mapping(source = "price", target = "dishInfo.price")
    @Mapping(source = "description", target = "dishInfo.description")
    @Mapping(source = "urlImage", target = "dishInfo.urlImage")
    @Mapping(source = "category", target = "dishInfo.category")
    Dish toModel(DishEntity dishEntity);
}
