package com.service.small.square.domain.model.order.efficiency;

public class OrderEfficiency {
    private Long orderId;
    private long timeInMinutes;

    public OrderEfficiency(Long orderId, long timeInMinutes) {
        this.orderId = orderId;
        this.timeInMinutes = timeInMinutes;
    }

    public Long getOrderId() {
        return orderId;
    }

    public long getTimeInMinutes() {
        return timeInMinutes;
    }
}