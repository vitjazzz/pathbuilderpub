package com.vitja.study.degree.repository;

import com.vitja.study.degree.model.Review;
import com.vitja.study.degree.model.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    List<Review> getReviewsByVenueId(String venueId);
}
