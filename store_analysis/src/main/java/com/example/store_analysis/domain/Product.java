package com.example.store_analysis.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;




@Entity
public class Product {
    @Id
    private String url;
    private String product_name;
    private String type;
    private int price;
    private String store;
    private float rating;

    public Product() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public String getStore() {
        return store;
    }

    public float getRating() {
        return rating;
    }

    public String getBrand() {
        return brand;
    }

    public String getImg() {
        return img;
    }

    private String brand;

    public Product(String url, String product_name, String type, int price, String store, float rating, String brand, String img) {
        this.url = url;
        this.product_name = product_name;
        this.type = type;
        this.price = price;
        this.store = store;
        this.rating = rating;
        this.brand = brand;
        this.img = img;
    }

    private String img;

}


