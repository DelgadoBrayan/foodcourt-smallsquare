package com.service.small.square.domain.api;


import com.service.small.square.domain.model.Restaurant;


public interface IRestaurantServicePort {
    Restaurant saveRestaurant(Restaurant restaurant, String token);

}
