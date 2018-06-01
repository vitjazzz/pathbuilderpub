package com.vitja.study.degree.service.object;

import com.vitja.study.degree.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    Venue save(Venue venue);
    Optional<Venue> getVenue(String id);
    Optional<Venue> getVenueByName(String name);
    Page<Venue> getVenues(Pageable page);
    void deleteVenue(String id);
    List<Venue> getVenuesInRadius(Double lat, Double lon, Double radius);
}
