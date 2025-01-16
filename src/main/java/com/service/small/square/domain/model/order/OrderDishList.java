package com.service.small.square.domain.model.order;

import java.time.LocalDateTime;
import java.util.List;

import com.service.small.square.application.dto.dish.DishDto;

public class OrderDishList {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private OrderStatus status;
    private Long chefId;
    private Long restaurantId;
    private List<DishDto> listDishes;
    
    public OrderDishList(Long id, Long clientId, LocalDateTime date, OrderStatus status, Long chefId, Long restaurantId,
            List<DishDto> listDishes) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
        this.chefId = chefId;
        this.restaurantId = restaurantId;
        this.listDishes = listDishes;
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

    public List<DishDto> getListDishes() {return listDishes;}
    public void setListDishes(List<DishDto> listDishes) {this.listDishes = listDishes;}

    
}
