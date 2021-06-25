package com.project.restaurantmanager.Data;

import java.util.Locale;

public class FoodItems {
    String name;
    String Category;
    Double Price;
    String image;

    public FoodItems(String name, Double price,String image) {
        this.name = name;
        Price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public FoodItems setName(String name) {
        this.name = name;
        return this;
    }


    public String getCategory() {
        return Category;
    }

    public FoodItems setCategory(String category) {
        Category = category;
        return this;
    }

    public Double getPrice() {
        return Price;
    }

    public FoodItems setPrice(Double price) {
        Price = price;
        return this;
    }

    public String getImage() {
        return image;
    }

    public FoodItems setImage(String image) {
        this.image = image;
        return this;
    }

}
