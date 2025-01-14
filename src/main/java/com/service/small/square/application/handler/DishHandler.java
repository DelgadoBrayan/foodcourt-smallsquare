package com.service.small.square.application.handler;

import org.springframework.stereotype.Service;

import com.service.small.square.application.dto.dish.DishDto;
import com.service.small.square.application.dto.dish.UpdateDishActive;
import com.service.small.square.application.dto.dish.UpdateDishDto;
import com.service.small.square.application.mapper.DishMapper;
import com.service.small.square.domain.api.IDishServicePort;
import com.service.small.square.domain.model.dish.Dish;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler {

    private final IDishServicePort dishServicePort;
    private final DishMapper dishMapper;

    public void createDish(DishDto dishDto, String token) {
        Dish dish = dishMapper.toDish(dishDto);
        dishServicePort.saveDish(dish, token);
    }

    public void updateDish(Long id, UpdateDishDto updateDishDTO) {
        Dish dish = dishServicePort.findDishById(id);
        Dish updatedDish = dishMapper.updateDishFromDTO(dish, updateDishDTO);
        dishServicePort.updateDish(id, updatedDish.getDishInfo().getPrice(), updatedDish.getDishInfo().getDescription());
    }

    public void toggleDishAvailability(Long id, UpdateDishActive updateDishActive, String token) {
        Dish dish = dishServicePort.findDishById(id);
        Dish updateDish = dishMapper.updateDishAvailability(dish, updateDishActive);
        dishServicePort.toggleDishAvailability(id, updateDish.isActive(), token);
    }
}
