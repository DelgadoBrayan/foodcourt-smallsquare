package com.service.small.square.application.handler;

import org.springframework.stereotype.Service;

import com.service.small.square.application.dto.order.OrderDishDto;
import com.service.small.square.application.mapper.OrderDishMapper;
import com.service.small.square.domain.api.IOrderDishServicePort;
import com.service.small.square.domain.model.order.OrderDish;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDishHandler {
    private final IOrderDishServicePort orderDishServicePort;
    private final OrderDishMapper orderDishMapper;

    public OrderDishDto createOrderDish(OrderDishDto orderDishDto){
        OrderDish order = orderDishMapper.toDomain(orderDishDto);
        OrderDish saveOrder = orderDishServicePort.saveOrderDish(order);

         return orderDishMapper.toDto(saveOrder);
    }
}
