package com.vitja.study.degree.service.bp.impl;

import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.service.bp.MapService;
import com.vitja.study.degree.service.object.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.*;

@Service
public class MapServiceImpl implements MapService {
    private VenueService venueService;
    private static Double EARTH_RADIUS = 6365.578;

    @Autowired
    public MapServiceImpl(VenueService venueService) {
        this.venueService = venueService;
    }

    @Override
    public List<Venue> getVenuesInRadiusList(Double radius, Double lat, Double lon) {
        return venueService
                .getVenuesInRadius(lat, lon, radius / 50)
                .stream().filter(venue ->
                        this.getDistance(lat, lon, venue.getLat(), venue.getLon()) < radius
                )
                .collect(Collectors.toList());
    }

    @Override
    public Double getDistance(Double startLat, Double startLon, Double endLat, Double endLon) {
        double startLatRad = toRadians(startLat);
        double startLonRad = toRadians(startLon);

        double endLatRad = toRadians(endLat);
        double endLonRad = toRadians(endLon);

        double result = acos((sin(startLatRad) * sin(endLatRad) +
                cos(startLatRad) * cos(endLatRad) * cos(startLonRad - endLonRad))) * EARTH_RADIUS;
        return result;
    }
}
