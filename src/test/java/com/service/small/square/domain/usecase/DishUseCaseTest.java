package com.service.small.square.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.service.small.square.domain.model.dish.Dish;
import com.service.small.square.domain.model.dish.DishInfo;
import com.service.small.square.domain.spi.IDishPersistencePort;
import com.service.small.square.domain.spi.IRestaurantPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidDishException;

class DishUseCaseTest {

    @InjectMocks
    private DishUseCase dishUseCase;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDish_InvalidDish_ThrowsException() {
        
        DishInfo dishInfo =new DishInfo("",0.0,"test example " ,"http://logo.com","test");
        Dish invalidDish = new Dish(1L,dishInfo,1L,true);

        InvalidDishException exception = assertThrows(InvalidDishException.class, () ->
            dishUseCase.saveDish(invalidDish, "valid_token"));
        assertEquals("El precio debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void findDishById_ValidId_ReturnsDish() {
        
        DishInfo dishInfo =new DishInfo("example",12.0,"test example " ,"http://logo.com","test");
        Dish dish = new Dish(1L,dishInfo,1L,true);
        when(dishPersistencePort.findDishById(1L)).thenReturn(dish);
        Dish result = dishUseCase.findDishById(1L);

    
        assertNotNull(result);
        assertEquals(dish.getRestaurantId(), result.getRestaurantId());
        verify(dishPersistencePort, times(1)).findDishById(1L);
    }

    @Test
    void updateDish_ValidIdAndData_Success() {

        DishInfo dishInfo =new DishInfo("example",12.0,"test example " ,"http://logo.com","test");
        Dish dish = new Dish(1L,dishInfo,1L,true);
        when(dishPersistencePort.findDishById(1L)).thenReturn(dish);

        assertDoesNotThrow(() -> dishUseCase.updateDish(1L, 200.0, "New Description"));

        assertEquals(200.0, dish.getDishInfo().getPrice());
        assertEquals("New Description", dish.getDishInfo().getDescription());
        verify(dishPersistencePort, times(1)).saveDish(dish);
    }


    @Test
    void listDishesByRestaurant_ValidData_ReturnsDishes() {
        Long restaurantId = 1L;
        DishInfo dishInfo =new DishInfo("example",12.0,"test example " ,"http://logo.com","test");
        Dish dish = new Dish(1L,dishInfo,1L,true);
        List<Dish> dishes = List.of(dish);
        when(dishPersistencePort.listDishesByRestaurant(restaurantId, 0, 10, "Main Course")).thenReturn(dishes);

        List<Dish> result = dishUseCase.listDishesByRestaurant(restaurantId, 0, 10, "Main Course");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(dishPersistencePort, times(1)).listDishesByRestaurant(restaurantId, 0, 10, "Main Course");
    }
}