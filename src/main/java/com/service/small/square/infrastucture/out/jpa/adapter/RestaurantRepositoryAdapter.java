package com.service.small.square.infrastucture.out.jpa.adapter;

import com.service.small.square.domain.model.Restaurant;
import com.service.small.square.domain.spi.IRestaurantPersistencePort;
import com.service.small.square.infrastucture.out.jpa.entity.RestaurantEntity;
import com.service.small.square.infrastucture.out.jpa.mapper.RestaurantEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.RestaurantJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantRepositoryAdapter implements IRestaurantPersistencePort {
    private final RestaurantJpaRepository repository;
    private final RestaurantEntityMapper mapper;


    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = mapper.toEntity(restaurant);
        return mapper.toDomain(repository.save(restaurantEntity));
    }

}