package com.service.small.square.application.dto.order;

import lombok.Data;

@Data
public class OrderDishDto {
    private Long id;
    private Long orderId;
    private Long dishId;
    private int quantity;
}