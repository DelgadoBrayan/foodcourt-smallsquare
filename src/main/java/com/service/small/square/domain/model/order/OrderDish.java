package com.service.small.square.domain.model.order;

public class OrderDish {
    private Long id;
    private Long orderId;
    private Long dishId;
    private int quantity;

    public OrderDish(Long id, Long orderId, Long dishId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
    }
    
    public OrderDish() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Long getOrderId() { return orderId;}
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getDishId() {return dishId;}
    public void setDishId(Long dishId) {this.dishId = dishId;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    
}