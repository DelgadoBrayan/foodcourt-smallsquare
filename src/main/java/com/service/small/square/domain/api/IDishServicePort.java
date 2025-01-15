package com.service.small.square.domain.api;

import java.util.List;

import com.service.small.square.domain.model.dish.Dish;

public interface IDishServicePort {
    void saveDish(Dish dish, String token);

    void updateDish(Long id, Double price, String description);

    void toggleDishAvailability(Long id, boolean isAvailable, String token);

    Dish findDishById(Long id);

    List<Dish> listDishesByRestaurant(Long restaurantId, int page, int size, String category);

}
