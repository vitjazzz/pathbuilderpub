package com.vitja.study.degree.controller;


import com.vitja.study.degree.dto.PathRequestDTO;
import com.vitja.study.degree.exception.VenueNotFoundException;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.service.bp.MapService;
import com.vitja.study.degree.service.bp.RouteService;
import com.vitja.study.degree.service.object.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/degree/venue")
public class VenueController {
    private VenueService venueService;
    private MapService mapService;
    private RouteService routeService;

    @Autowired
    public VenueController(VenueService venueService, MapService mapService, RouteService routeService) {
        this.venueService = venueService;
        this.mapService = mapService;
        this.routeService = routeService;
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {

        Venue createdVenue = venueService.save(venue);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdVenue.getId()).toUri();
        return ResponseEntity.created(location).body(createdVenue);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Venue> getVenue(@PathVariable String id) {
        Optional<Venue> venue = venueService.getVenue(id);
        if (!venue.isPresent()) {
            throw new VenueNotFoundException("ID: " + id);
        }
        return ResponseEntity.ok(venue.get());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, params = {"page", "size"})
    public ResponseEntity<Page<Venue>> getVenuesList(@RequestParam("page") int page,
                                                     @RequestParam("size") int size) {
        Page<Venue> venueListPage = venueService.getVenues(new QPageRequest(page - 1, size));

        return ResponseEntity.ok(venueListPage);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/inRadius", method = RequestMethod.GET)
    public ResponseEntity<List<Venue>> getVenuesInRadiusList(@RequestParam("radius") int radius,
                                                             @RequestParam("lat") double lat,
                                                             @RequestParam("lon") double lon) {

        return ResponseEntity.ok(mapService.getVenuesInRadiusList((double) radius, lat, lon));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/path", method = RequestMethod.POST)
    public ResponseEntity<List<Venue>> buildPath(@RequestBody PathRequestDTO pathRequestDTO) {

        return ResponseEntity.ok(routeService.buildPath( pathRequestDTO.radius,
                pathRequestDTO.lat, pathRequestDTO.lon, pathRequestDTO.venuesAmount));
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteVenue(@PathVariable String id) {
        venueService.deleteVenue(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
