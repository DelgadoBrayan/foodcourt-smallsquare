package com.service.small.square.application.dto.order.efficiency;

import lombok.Data;

@Data
public class OrderEfficiencyDto {
    private Long orderId;
    private long timeInMinutes;
}
