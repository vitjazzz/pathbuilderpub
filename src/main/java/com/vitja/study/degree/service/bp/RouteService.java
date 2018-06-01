package com.vitja.study.degree.service.bp;

import com.vitja.study.degree.model.Venue;

import java.util.Date;
import java.util.List;

public interface RouteService {
    List<Venue> buildPath(int radius, double lat, double lon, int venuesAmount);
}
