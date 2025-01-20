package com.service.small.square.domain.usecase;

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

import com.service.small.square.domain.model.Restaurant;
import com.service.small.square.domain.spi.IRestaurantPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidRestaurantException;


class CreateRestaurantUseCaseTest {

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void saveRestaurant_InvalidRestaurant_ThrowsException() {
        Restaurant invalidRestaurant = new Restaurant(1L,"", "1233242314", "address", "3219843233", "", 1L);

        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class, () ->
            createRestaurantUseCase.saveRestaurant(invalidRestaurant, "valid_token"));
        assertEquals("El nombre del restaurante no debe estar vacío o nulo", exception.getMessage());
    }

    @Test
    void getAllRestaurants_ValidPageAndSize_ReturnsRestaurants() {
        int page = 0;
        int size = 10;
        List<Restaurant> restaurants = List.of(new Restaurant(2L,"Restaurant 1", "123", "Address", "+123", "http://logo1.com", 1L));

        when(restaurantPersistencePort.getAllRestaurants(page, size)).thenReturn(restaurants);

        List<Restaurant> result = createRestaurantUseCase.getAllRestaurants(page, size);


        assertNotNull(result);
        assertEquals(1, result.size());
        verify(restaurantPersistencePort, times(1)).getAllRestaurants(page, size);
    }

    @Test
    void getAllRestaurants_InvalidPageOrSize_ThrowsException() {
        int page = -1;
        int size = 0;

        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class, () ->
            createRestaurantUseCase.getAllRestaurants(page, size));
        assertEquals("La página debe ser mayor o igual a 0 y el tamaño mayor a 0.", exception.getMessage());
    }
}

