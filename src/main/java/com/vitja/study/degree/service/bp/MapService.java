package com.vitja.study.degree.service.bp;

import com.vitja.study.degree.model.Venue;

import java.util.List;

public interface MapService {
    List<Venue> getVenuesInRadiusList(Double radius, Double lat, Double lon);
    Double getDistance(Double startLat, Double startLon, Double endLat, Double endLon);
}
