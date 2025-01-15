package com.service.small.square.domain.api;


import java.util.List;

import com.service.small.square.domain.model.Restaurant;


public interface IRestaurantServicePort {
    Restaurant saveRestaurant(Restaurant restaurant, String token);
    Restaurant findRestaurantById(Long restaurantId);
    List<Restaurant> getAllRestaurants(int page, int size);
}
