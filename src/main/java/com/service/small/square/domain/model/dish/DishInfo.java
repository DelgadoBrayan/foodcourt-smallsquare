package com.service.small.square.domain.model.dish;

public class DishInfo {
    private String name;
    private Double price;
    private String description;
    private String urlImage;
    private String category;

    
    public DishInfo() {}

    public DishInfo(String name, Double price, String description, String urlImage, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.urlImage = urlImage;
        this.category = category;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrlImage() { return urlImage; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "DishInfo [name=" + name + ", price=" + price + ", description=" + description + ", urlImage=" + urlImage
                + ", category=" + category + "]";
    }

    
}
