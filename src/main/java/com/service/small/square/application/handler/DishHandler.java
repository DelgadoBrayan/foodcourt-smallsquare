package com.service.small.square.application.handler;

import java.util.List;

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

    public List<DishDto> listDishesByRestaurant(Long restaurantId, int page, int size, String category) {
        List<Dish> dishes = dishServicePort.listDishesByRestaurant(restaurantId, page, size, category);
        return dishMapper.toDishDTOList(dishes);
    }
}
