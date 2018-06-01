package com.vitja.study.degree.service.object.impl;


import com.vitja.study.degree.model.Role;
import com.vitja.study.degree.model.User;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.repository.UserRepository;
import com.vitja.study.degree.repository.VenueRepository;
import com.vitja.study.degree.service.object.UserService;
import com.vitja.study.degree.service.object.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VenueServiceImpl implements VenueService {
    private VenueRepository venueRepository;

    @Autowired
    public VenueServiceImpl(VenueRepository venueRepository){
        this.venueRepository = venueRepository;
    }

    @Override
    public Optional<Venue> getVenue(String id) {
        return venueRepository.findById(id);
    }

    @Override
    public Optional<Venue> getVenueByName(String name) {
        return venueRepository.findByName(name);
    }

    @Override
    public Page<Venue> getVenues(Pageable page) {
        return venueRepository.findAll(page);
    }

    @Override
    public Venue save(Venue venue) {
        venue.setId(UUID.randomUUID().toString());
        return venueRepository.save(venue);
    }

    @Override
    public void deleteVenue(String id) {
        venueRepository.deleteById(id);
    }

    @Override
    public List<Venue> getVenuesInRadius(Double lat, Double lon, Double radius) {
        return venueRepository.findByLocation(lat, lon, radius);
    }
}
