package com.vitja.study.degree.controller;
import com.vitja.study.degree.dto.RatingDTO;
import com.vitja.study.degree.dto.ReviewDTO;
import com.vitja.study.degree.exception.UserNotFoundException;
import com.vitja.study.degree.model.Rating;
import com.vitja.study.degree.model.Review;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.service.object.RatingService;
import com.vitja.study.degree.service.object.ReviewService;
import com.vitja.study.degree.service.object.UserService;
import com.vitja.study.degree.service.object.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/degree/review")
public class ReviewController {

    private ReviewService reviewService;
    private UserService userService;
    private VenueService venueService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, VenueService venueService){
        this.reviewService = reviewService;
        this.userService = userService;
        this.venueService = venueService;
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public HttpEntity<Object> createReview(@RequestBody ReviewDTO reviewDTO, Principal principal){
        Optional<User> user = userService.getUserByEmail(principal.getName());
        Optional<Venue> venue = venueService.getVenue(reviewDTO.venueId);
        if(!user.isPresent() || !venue.isPresent()){
            throw new UserNotFoundException("Not found.");
        }
        reviewService.save(user.get(), venue.get(), reviewDTO.review);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ReviewDTO getReview(Principal principal, @RequestParam("venueId") String venueId){
        Optional<User> user = userService.getUserByEmail(principal.getName());
        Optional<Review> review = reviewService.getReview(user.get().getId(), venueId);
        if(!review.isPresent()){
            throw new RuntimeException("Review is missing.");
        }
        ReviewDTO result = new ReviewDTO(venueId, review.get().getUser().getLastName(), review.get().getReview());

        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/venue",method = RequestMethod.GET)
    public List<ReviewDTO> getVenueReviews(@RequestParam("venueId") String venueId){
        List<Review> reviews = reviewService.getVenueReviews(venueId);
        if(reviews.isEmpty()){
            return new ArrayList<>();
        }

        return reviews.stream()
                .map(review ->
                        new ReviewDTO(review.getVenue().getId(), review.getUser().getLastName(), review.getReview())
                )
                .collect(Collectors.toList());
    }
}
