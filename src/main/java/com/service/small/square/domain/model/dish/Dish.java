package com.service.small.square.domain.model.dish;

public class Dish {
    private Long id;
    private DishInfo dishInfo;
    private Long idRestaurante;
    private Boolean active;

    
    public Dish() {}

    public Dish(Long id, DishInfo dishInfo, Long idRestaurante, Boolean active) {
        this.id = id;
        this.dishInfo = dishInfo;
        this.idRestaurante = idRestaurante;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DishInfo getDishInfo() { return dishInfo; }
    public void setDishInfo(DishInfo dishInfo) { this.dishInfo = dishInfo; }

    public Long getRestaurantId() { return idRestaurante; }
    public void setRestaurantId(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public boolean isActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public void updatePrice(Double price) { 
        this.dishInfo.setPrice(price); 
    } 
    public void updateDescription(String description) { 
        this.dishInfo.setDescription(description); 
    }

    @Override
    public String toString() {
        return "Dish [id=" + id + ", dishInfo=" + dishInfo + ", restaurantAssociation=" + idRestaurante
                + ", active=" + active + "]";
    }
    
}
