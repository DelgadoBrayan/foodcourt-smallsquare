package com.service.small.square.application.handler;

import java.util.List;

import org.springframework.stereotype.Service;

import com.service.small.square.application.dto.restaurant.RestaurantRequestDto;
import com.service.small.square.application.dto.restaurant.RestaurantResponseList;
import com.service.small.square.application.mapper.RestaurantMapper;
import com.service.small.square.domain.model.Restaurant;
import com.service.small.square.domain.usecase.CreateRestaurantUseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateRestaurantHandler {
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final RestaurantMapper restaurantMapper;


    public RestaurantRequestDto saveRestaurant(RestaurantRequestDto dto, String token) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        Restaurant savedRestaurant = createRestaurantUseCase.saveRestaurant(restaurant, token);
        return restaurantMapper.toDto(savedRestaurant);
    }

    public RestaurantRequestDto findRestaurantById(Long restaurantId) {
        Restaurant restaurant = createRestaurantUseCase.findRestaurantById(restaurantId);
        return restaurantMapper.toDto(restaurant);
    }


        public List<RestaurantResponseList> getRestaurantsOrderedAndPaginated(int page, int size) {
        List<Restaurant> restaurants = createRestaurantUseCase.getAllRestaurants(page, size);

        return restaurantMapper.toResponseList(restaurants);
    }
}