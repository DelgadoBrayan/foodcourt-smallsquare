package com.service.small.square.domain.api;

import com.service.small.square.domain.model.dish.Dish;

public interface IDishServicePort {
    void saveDish(Dish dish, String token);

    void updateDish(Long id, Double price, String description);

    void toggleDishAvailability(Long id, boolean isAvailable, String token);

    Dish findDishById(Long id);

}
