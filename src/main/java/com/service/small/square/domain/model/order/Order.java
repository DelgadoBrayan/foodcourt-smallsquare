package com.service.small.square.domain.model.order;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private OrderStatus status;
    private Long chefId;
    private Long restaurantId;
    private String pin;
    private LocalDateTime orderFinished;
    public Order(Long id, Long clientId, LocalDateTime date, OrderStatus status, Long chefId, Long restaurantId, String pin, LocalDateTime orderFinished) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
        this.chefId = chefId;
        this.restaurantId = restaurantId;
        this.pin = pin;
        this.orderFinished = orderFinished;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getClientId() {return clientId;}
    public void setClientId(Long clientId) {this.clientId = clientId;}
    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {this.date = date;}
    public OrderStatus getStatus() {return status;}
    public void setStatus(OrderStatus status) {this.status = status;}
    public Long getChefId() {return chefId;}
    public void setChefId(Long chefId) {this.chefId = chefId;}
    public Long getRestaurantId() {return restaurantId;}
    public void setRestaurantId(Long restaurantId) {this.restaurantId = restaurantId;}
    public String getPin() {return pin;}
    public void setPin(String pin) {this.pin = pin;}
    public LocalDateTime getOrderFinished() {return orderFinished;}
    public void setOrderFinished(LocalDateTime orderFinished) {this.orderFinished = orderFinished;}
    @Override
    public String toString() {
        return "Order [id=" + id + ", clientId=" + clientId + ", date=" + date + ", status=" + status + ", chefId="
                + chefId + ", restaurantId=" + restaurantId + "]";
    }

    
}