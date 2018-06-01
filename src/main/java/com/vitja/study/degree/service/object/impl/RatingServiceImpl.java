package com.vitja.study.degree.service.object.impl;


import com.vitja.study.degree.model.Rating;
import com.vitja.study.degree.model.RatingId;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.repository.RatingRepository;
import com.vitja.study.degree.service.object.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating save(User user, Venue venue, Integer rating) {
        return ratingRepository.save(new Rating(user, venue, rating));
    }

    @Override
    public void changeRating(String userId, String venueId, Integer newRating) {
        Optional<Rating> rating = ratingRepository.getByUser_IdAndVenue_Id(userId, venueId);
        if(!rating.isPresent()){
            rating.get().setRating(newRating);
            ratingRepository.save(rating.get());
        }
    }

    @Override
    public Optional<Rating> getRating(String userId, String venueId) {
        return ratingRepository.findById(new RatingId(userId, venueId));
    }

}
