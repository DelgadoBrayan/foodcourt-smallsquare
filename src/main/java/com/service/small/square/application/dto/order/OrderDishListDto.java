package com.service.small.square.application.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import com.service.small.square.application.dto.dish.DishDto;

import lombok.Data;

@Data
public class OrderDishListDto {
    private Long clientId;
    private LocalDateTime date;
    private String status;
    private Long chefId;
    private Long restaurantId;
    private List<DishDto> listDishes;
}
