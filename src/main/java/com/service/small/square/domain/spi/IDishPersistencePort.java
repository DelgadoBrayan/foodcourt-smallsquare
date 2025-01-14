package com.service.small.square.domain.spi;

import com.service.small.square.domain.model.dish.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

    void updateDish(Long id, Double price, String description);

    void toggleDishAvailability(Long id, boolean isAvailable);

    Dish findDishById(Long id);

}
