package com.service.small.square.application.handler;

import java.util.List;
import org.springframework.stereotype.Service;

import com.service.small.square.application.dto.order.OrderDto;
import com.service.small.square.application.mapper.OrderMapper;
import com.service.small.square.domain.api.IOrderServicePort;
import com.service.small.square.domain.model.order.Order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler {

    private final IOrderServicePort orderServicePort;
    private final OrderMapper orderMapper;

    public OrderDto createOrder(OrderDto orderDto, List<Long> orderDish) {
        Order order = orderMapper.toDomain(orderDto);
        Order savedOrder = orderServicePort.createOrder(order, orderDish);
        return orderMapper.toDto(savedOrder);
    }

    public List<OrderDto> getOrdersByStatus(String status, Long restaurantId, int page, int size) {
        List<Order> orders = orderServicePort.getOrdersByStatus(status, restaurantId, page, size);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }
}