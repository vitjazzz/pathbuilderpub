package com.vitja.study.degree.service.object;

import com.vitja.study.degree.model.Rating;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.model.Venue;

import java.util.Optional;

public interface RatingService {
    Rating save(User user, Venue venue, Integer rating);
    void changeRating(String userId, String venueId, Integer newRating);
    Optional<Rating> getRating(String userId, String venueId);
}
