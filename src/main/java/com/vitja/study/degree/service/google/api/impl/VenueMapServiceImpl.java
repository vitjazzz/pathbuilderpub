package com.vitja.study.degree.service.google.api.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.service.google.api.VenueMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class VenueMapServiceImpl implements VenueMapService {
    private static final Logger logger = LoggerFactory.getLogger(VenueMapServiceImpl.class);

    @Value("${google.api.key}")
    private String GOOGLE_API_KEY;

    @Override
    public List<Venue> getVenues(LatLng location, Integer radius) {
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(GOOGLE_API_KEY)
                .build();

        return null;
    }
}
