package com.gianfranco.simulatedannealing.model;

public interface DistanceCalculator {

    double distanceBetween(City begin, City end);

    DistanceCalculator PYTHAGOREAN_CALCULATOR = (begin, end) -> {
        double x = Math.abs(begin.x() - end.x());
        double y = Math.abs(begin.y() - end.y());
        return Math.sqrt(x * x + y * y);
    };
}
