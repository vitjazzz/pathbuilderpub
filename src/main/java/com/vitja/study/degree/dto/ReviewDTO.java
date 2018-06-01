package com.vitja.study.degree.dto;



public class ReviewDTO {
    public String venueId;
    public String username;

    public String review;

    public ReviewDTO(String venueId, String username, String review) {
        this.venueId = venueId;
        this.username = username;
        this.review = review;
    }

    public ReviewDTO() {
    }
}
