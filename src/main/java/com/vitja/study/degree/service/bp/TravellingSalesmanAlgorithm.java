package com.vitja.study.degree.service.bp;

import java.util.List;

public interface TravellingSalesmanAlgorithm {
    List<Integer> getBestPath(double[][] distanceMatrix, int maxNodes);
}
