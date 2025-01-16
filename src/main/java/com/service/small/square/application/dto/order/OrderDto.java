package com.service.small.square.application.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import com.service.small.square.domain.model.order.OrderStatus;

import lombok.Data;
@Data
public class OrderDto {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private OrderStatus status;
    private Long chefId;
    private Long restaurantId;
    private List<Long> listDishes;
}
