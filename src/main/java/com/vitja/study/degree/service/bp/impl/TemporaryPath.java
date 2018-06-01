package com.vitja.study.degree.service.bp.impl;

import java.util.List;

class TemporaryPath {
    Double distance;
    List<Integer> path;

    TemporaryPath(Double distance, List<Integer> path) {
        this.distance = distance;
        this.path = path;
    }

    Double getDistance() {
        return distance;
    }

    List<Integer> getPath() {
        return path;
    }
}
