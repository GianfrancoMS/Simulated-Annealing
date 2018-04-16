package com.gianfranco.simulatedannealing.model;

public final class Point {

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }
}
