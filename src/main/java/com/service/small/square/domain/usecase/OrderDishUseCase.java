package com.service.small.square.domain.usecase;

import org.springframework.stereotype.Service;

import com.service.small.square.domain.api.IOrderDishServicePort;
import com.service.small.square.domain.model.order.OrderDish;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDishUseCase implements  IOrderDishServicePort{

    private final IOrderDishPersistencePort orderDishPersistencePort;
    @Override
    public OrderDish saveOrderDish(OrderDish orderDish) {
        
      return orderDishPersistencePort.saveOrderDish(orderDish);

    }

    
}
