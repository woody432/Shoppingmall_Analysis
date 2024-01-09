package com.example.store_analysis.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(ReviewId.class)
public class Review {
    @Id
    private String url;
    @Id
    private long review_id;
    private String gender;
    private float height;
    private float weight;

    public Review() {
        super();
    }

    public Review(String url, long review_id, String gender, float height, float weight) {
        this.url = url;
        this.review_id = review_id;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {this.review_id = review_id;}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}


