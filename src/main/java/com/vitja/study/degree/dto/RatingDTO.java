package com.vitja.study.degree.dto;



public class RatingDTO {
    public String venueId;

    public Integer rating;

    public RatingDTO(String venueId, Integer rating) {
        this.venueId = venueId;
        this.rating = rating;
    }

    public RatingDTO() {
    }
}
