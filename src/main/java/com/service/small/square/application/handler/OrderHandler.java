package com.service.small.square.application.handler;

import java.util.List;

import org.springframework.stereotype.Service;

import com.service.small.square.application.dto.order.OrderDishListDto;
import com.service.small.square.application.dto.order.OrderDto;
import com.service.small.square.application.mapper.OrderDishListMapper;
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
    private final OrderDishListMapper orderDishMapper;

    public OrderDto createOrder(OrderDto orderDto, List<Long> orderDish) {
        Order order = orderMapper.toDomain(orderDto);
        Order savedOrder = orderServicePort.createOrder(order, orderDish);
        return orderMapper.toDto(savedOrder);
    }

    public List<OrderDishListDto> handleGetOrdersByStatus(String status, Long restaurantId, int page, int size) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status is required.");
        }
        if (restaurantId == null || restaurantId <= 0) {
            throw new IllegalArgumentException("Restaurant ID must be valid.");
        }
        return orderServicePort.getOrdersByStatus(status, restaurantId, page, size)
            .stream()
            .map(orderDishMapper::toDTO)
            .toList();
    }

    public void assignEmployeeToOrder(Long orderId, Long employeeId, Long restaurantId) {
        orderServicePort.assignEmployeeToOrder(orderId, employeeId, restaurantId);
    }

    public void  orderReady (Long orderId, String token){
        orderServicePort.noticationOrderReady(orderId, token);
    }
}