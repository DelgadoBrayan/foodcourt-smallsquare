package com.service.small.square.infrastucture.out.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.small.square.domain.model.Restaurant;
import com.service.small.square.infrastucture.out.jpa.entity.RestaurantEntity;


@Mapper(componentModel = "spring")
public interface RestaurantEntityMapper {
   
    @Mapping(source = "urlLogo", target = "logoUrl")
    @Mapping(source = "ownerId", target = "ownerId")
    RestaurantEntity toEntity(Restaurant restaurant);

    @Mapping(source = "logoUrl", target = "urlLogo")
    @Mapping(source = "ownerId", target = "ownerId")
    Restaurant toDomain(RestaurantEntity entity);
}