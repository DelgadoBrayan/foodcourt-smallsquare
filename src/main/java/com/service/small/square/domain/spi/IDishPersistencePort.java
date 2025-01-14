package com.service.small.square.domain.spi;

import com.service.small.square.domain.model.dish.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);

}
