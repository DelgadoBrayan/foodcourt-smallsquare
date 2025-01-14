package com.service.small.square.infrastucture.out.jpa.adapter;

import com.service.small.square.domain.model.dish.Dish;
import com.service.small.square.domain.spi.IDishPersistencePort;
import com.service.small.square.infrastucture.out.jpa.mapper.DishEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.DishRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final DishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) { 
        dishRepository.save(dishEntityMapper.toEntity(dish)); 
    }

}
