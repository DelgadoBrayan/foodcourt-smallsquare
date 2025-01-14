package com.service.small.square.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.service.small.square.application.dto.restaurant.RestaurantRequestDto;
import com.service.small.square.domain.model.Restaurant;



@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "urlLogo", source = "urlLogo")
    @Mapping(target = "ownerId", source = "ownerId")
    Restaurant toEntity(RestaurantRequestDto dto);

    @Mapping(target = "urlLogo", source = "urlLogo")
    RestaurantRequestDto toDto(Restaurant restaurant);

}
