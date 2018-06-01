package com.vitja.study.degree.service.google.api;

import com.google.maps.model.LatLng;
import com.vitja.study.degree.model.Venue;

import java.util.List;

public interface VenueMapService {
    List<Venue> getVenues(LatLng location, Integer radius);
}
