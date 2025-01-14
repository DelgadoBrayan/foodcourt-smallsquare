package com.service.small.square.application.dto.dish;

import lombok.Data;

@Data
public class DishDto {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String urlImage;
    private String category;
    private Long restaurantId;
    private Boolean active;
}
