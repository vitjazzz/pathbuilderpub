package com.vitja.study.degree.repository;

import com.vitja.study.degree.model.Rating;
import com.vitja.study.degree.model.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    Optional<Rating> getByUser_IdAndVenue_Id(String userId, String venueId);
}
