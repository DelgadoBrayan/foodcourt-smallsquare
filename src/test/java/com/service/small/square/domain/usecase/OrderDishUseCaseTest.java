package com.service.small.square.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;

class OrderDishUseCaseTest {

    @InjectMocks
    private OrderDishUseCase orderDishUseCase;

    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrderDish_ValidOrderDish_Success() {
        // Arrange
        OrderDish orderDish = new OrderDish(1L,1L, 2L, 3);
        when(orderDishPersistencePort.saveOrderDish(orderDish)).thenReturn(orderDish);

        // Act
        OrderDish result = orderDishUseCase.saveOrderDish(orderDish);

        // Assert
        assertNotNull(result);
        assertEquals(orderDish.getDishId(), result.getDishId());
        assertEquals(orderDish.getOrderId(), result.getOrderId());
        assertEquals(orderDish.getQuantity(), result.getQuantity());
        verify(orderDishPersistencePort, times(1)).saveOrderDish(orderDish);
    }

    @Test
    void saveOrderDish_NullOrderDish_ThrowsException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> orderDishUseCase.saveOrderDish(null));
        verify(orderDishPersistencePort, never()).saveOrderDish(any());
    }
}
