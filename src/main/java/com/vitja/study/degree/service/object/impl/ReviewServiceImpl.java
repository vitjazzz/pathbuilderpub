package com.vitja.study.degree.service.object.impl;


import com.vitja.study.degree.model.Review;
import com.vitja.study.degree.model.ReviewId;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.repository.ReviewRepository;
import com.vitja.study.degree.service.object.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review save(User user, Venue venue, String review) {
        return reviewRepository.save(new Review(user, venue, review));
    }

    @Override
    public Optional<Review> getReview(String userId, String venueId) {
        return reviewRepository.findById(new ReviewId(userId, venueId));
    }

    @Override
    public List<Review> getVenueReviews(String venueId) {
        return reviewRepository.getReviewsByVenueId(venueId);
    }
}
