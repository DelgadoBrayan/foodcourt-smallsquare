package com.service.small.square.domain.api;

import com.service.small.square.domain.model.dish.Dish;

public interface IDishServicePort {
    void saveDish(Dish dish, String token);

}
