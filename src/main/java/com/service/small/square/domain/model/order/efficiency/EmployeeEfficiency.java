package com.service.small.square.domain.model.order.efficiency;

public class EmployeeEfficiency {
    private Long chefId;
    private double averageTimeInMinutes;

    public EmployeeEfficiency(Long chefId, double averageTimeInMinutes) {
        this.chefId = chefId;
        this.averageTimeInMinutes = averageTimeInMinutes;
    }

    public Long getChefId() {
        return chefId;
    }

    public double getAverageTimeInMinutes() {
        return averageTimeInMinutes;
    }
}