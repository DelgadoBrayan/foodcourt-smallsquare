package com.service.small.square.application.dto.order.efficiency;

import lombok.Data;

@Data
public class EmployeeEfficiencyDto {
    private Long chefId;
    private double averageTimeInMinutes;
}
