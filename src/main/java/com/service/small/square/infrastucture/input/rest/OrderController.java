package com.service.small.square.infrastucture.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.small.square.application.dto.order.OrderDto;
import com.service.small.square.application.handler.OrderHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderHandler orderHandler;

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderDto orderDto) {
        List<Long> listDishes = orderDto.getListDishes();
        orderHandler.createOrder(orderDto,listDishes);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(
            @RequestParam() String status,
            @RequestParam() Long restaurantId,
            @RequestParam() int page,
            @RequestParam() int size) {
        List<OrderDto> orders = orderHandler.getOrdersByStatus(status, restaurantId, page, size);
        return ResponseEntity.ok(orders);
    }
}
