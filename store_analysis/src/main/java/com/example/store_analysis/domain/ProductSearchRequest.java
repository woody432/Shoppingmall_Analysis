package com.example.store_analysis.domain;

public class ProductSearchRequest {

    private String user_id;
    private String product_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getProductName() {
        return product_name;
    }

    public ProductSearchRequest(String userId, String product_name) {
        this.user_id = userId;
        this.product_name = product_name;
    }
}
