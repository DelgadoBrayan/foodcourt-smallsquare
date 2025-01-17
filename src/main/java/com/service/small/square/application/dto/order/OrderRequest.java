package com.service.small.square.application.dto.order;


import java.util.List;

import lombok.Data;
@Data
public class OrderRequest {
    private Long id;
    private Long clientId;
    private Long restaurantId;
    private List<Long> listDishes;
}
