package com.service.small.square.infrastucture.out.jpa.adapter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Override
    public Restaurant findRestaurantById(Long restaurantId) {
        RestaurantEntity restaurantEntity = repository.findById(restaurantId).orElse(null);
        return restaurantEntity != null ? mapper.toDomain(restaurantEntity) : null;
    }

    @Override
    public List<Restaurant> getAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<RestaurantEntity> restaurantEntities = repository.findAll(pageable);
        return restaurantEntities.getContent().stream()
                .map(mapper::toDomain)
                .toList();
    }

}