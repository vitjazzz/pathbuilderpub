package com.vitja.study.degree.service.object;

import com.vitja.study.degree.model.Review;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.model.Venue;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review save(User user, Venue venue, String review);
    Optional<Review> getReview(String userId, String venueId);
    List<Review> getVenueReviews(String venueId);
}
