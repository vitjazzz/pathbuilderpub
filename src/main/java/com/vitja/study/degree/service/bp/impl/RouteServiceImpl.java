package com.vitja.study.degree.service.bp.impl;

import com.vitja.study.degree.model.Venue;
import com.vitja.study.degree.service.bp.MapService;
import com.vitja.study.degree.service.bp.RouteService;
import com.vitja.study.degree.service.bp.TravellingSalesmanAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    private MapService mapService;
    private TravellingSalesmanAlgorithm travellingSalesmanAlgorithm;

    @Autowired
    public RouteServiceImpl(MapService mapService, TravellingSalesmanAlgorithm travellingSalesmanAlgorithm) {
        this.mapService = mapService;
        this.travellingSalesmanAlgorithm = travellingSalesmanAlgorithm;
    }

    @Override
    public List<Venue> buildPath(int radius, double lat, double lon, int venuesAmount) {
        List<Venue> venuesInCircle = mapService.getVenuesInRadiusList((double) radius, lat, lon);
        if(venuesInCircle.isEmpty()){
            return new ArrayList<>();
        }
        venuesInCircle.sort(Comparator.comparing(Venue::getRating));
        int venuesInCircleSize = venuesInCircle.size() < 18 ? venuesInCircle.size() : 18;
        double[][] distanceMatrix = new double[venuesInCircleSize][venuesInCircleSize];
        for(int i = 0; i < venuesInCircleSize; i++){
            Venue iVenue = venuesInCircle.get(i);
            for (int j = 0; j < venuesInCircleSize; j++){
                if(i == j) {
                    distanceMatrix[i][j] = 0;
                    continue;
                }
                Venue jVenue = venuesInCircle.get(j);
                double distance = mapService.getDistance(iVenue.getLat(), iVenue.getLon(), jVenue.getLat(), jVenue.getLon());
                double jvenueRating = jVenue.getRating();
                distanceMatrix[i][j] =  jvenueRating < 3 ? distance * 1.67 : distance * (5.0 / jVenue.getRating());
            }
        }
        List<Integer> route = travellingSalesmanAlgorithm.getBestPath(distanceMatrix, venuesAmount);
        List<Venue> resultVenues = new ArrayList<>();
        for(Integer i : route){
            resultVenues.add(venuesInCircle.get(i));
        }
        return resultVenues;
    }
}
