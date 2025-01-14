package com.service.small.square.domain.spi;


import com.service.small.square.domain.model.Restaurant;


public interface IRestaurantPersistencePort {
    Restaurant saveRestaurant(Restaurant restaurant);
    Restaurant findRestaurantById(Long restaurantId);
}
