package com.service.small.square.domain.model.order;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private OrderStatus status;
    private Long chefId;
    private Long restaurantId;
    public Order(Long id, Long clientId, LocalDateTime date, OrderStatus status, Long chefId, Long restaurantId) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
        this.chefId = chefId;
        this.restaurantId = restaurantId;
       
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
    @Override
    public String toString() {
        return "Order [id=" + id + ", clientId=" + clientId + ", date=" + date + ", status=" + status + ", chefId="
                + chefId + ", restaurantId=" + restaurantId + "]";
    }

    
}