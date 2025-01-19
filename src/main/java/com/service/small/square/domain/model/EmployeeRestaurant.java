package com.service.small.square.domain.model;

public class EmployeeRestaurant {
    private Long emplooyeId;
    private Long restaurantId;

    
    public EmployeeRestaurant(Long restaurantId, Long emplooyeId) {
        this.restaurantId = restaurantId;
        this.emplooyeId = emplooyeId;
    }

    public Long getRestaurantId() {return restaurantId;}
    public void setRestaurantId(Long restaurantId) {this.restaurantId = restaurantId;}

    public Long getEmplooyeId() {return emplooyeId;}
    public void setEmplooyeId(Long emplooyeId) {this.emplooyeId = emplooyeId;}

}
