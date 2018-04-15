package com.gianfranco.simulatedannealing.model;

import java.util.List;

public final class Result {
    private final List<Place> places;
    private final int iterations;
    private final int replacements;
    private final double temperature;
    private final double distance;

    public Result(List<Place> places, int iterations, int replacements, double temperature, double distance) {
        this.places = places;
        this.iterations = iterations;
        this.replacements = replacements;
        this.temperature = temperature;
        this.distance = distance;
    }

    public List<Place> places() {
        return places;
    }

    public int iterations() {
        return iterations;
    }

    public int replacements() {
        return replacements;
    }

    public double temperature() {
        return temperature;
    }

    public double distance() {
        return distance;
    }
}
