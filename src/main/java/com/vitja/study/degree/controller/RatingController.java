package com.vitja.study.degree.controller;
import com.vitja.study.degree.dto.RatingDTO;
import com.vitja.study.degree.exception.UserNotFoundException;
import com.vitja.study.degree.model.Rating;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.service.object.RatingService;
import com.vitja.study.degree.service.object.UserService;
import com.vitja.study.degree.service.object.VenueService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/degree/rating")
public class RatingController {

    private RatingService ratingService;
    private UserService userService;
    private VenueService venueService;

    @Autowired
    public RatingController(RatingService ratingService, UserService userService, VenueService venueService){
        this.ratingService = ratingService;
        this.userService = userService;
        this.venueService = venueService;
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public HttpEntity<Object> createRating(@RequestBody RatingDTO ratingDTO, Principal principal){
        Optional<User> user = userService.getUserByEmail(principal.getName());
        Optional<Venue> venue = venueService.getVenue(ratingDTO.venueId);
        if(!user.isPresent() || !venue.isPresent()){
            throw new UserNotFoundException("Not found.");
        }
        ratingService.save(user.get(), venue.get(), ratingDTO.rating);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    @RequestMapping(method = RequestMethod.GET)
    public RatingDTO getRating(@RequestParam("userId") String userId, @RequestParam("venueId") String venueId){
        Optional<Rating> rating = ratingService.getRating(userId, venueId);
        if(!rating.isPresent()){
            throw new RuntimeException("Rating is missing.");
        }
        RatingDTO result = new RatingDTO(venueId, rating.get().getRating());

        return result;
    }
}
