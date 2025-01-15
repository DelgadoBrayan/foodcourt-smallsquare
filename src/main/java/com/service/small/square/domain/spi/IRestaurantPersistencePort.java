package com.service.small.square.domain.spi;


import java.util.List;

import com.service.small.square.domain.model.Restaurant;


public interface IRestaurantPersistencePort {
    Restaurant saveRestaurant(Restaurant restaurant);
    Restaurant findRestaurantById(Long restaurantId);
    List<Restaurant> getAllRestaurants(int page, int size);
}
