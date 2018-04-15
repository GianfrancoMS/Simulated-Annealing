package com.gianfranco.simulatedannealing.model;

import java.util.List;

public final class Result {
    private final List<City> cities;
    private final int iterations;
    private final int replacements;
    private final double temperature;
    private final double distance;

    public Result(List<City> cities, int iterations, int replacements, double temperature, double distance) {
        this.cities = cities;
        this.iterations = iterations;
        this.replacements = replacements;
        this.temperature = temperature;
        this.distance = distance;
    }

    public List<City> cities() {
        return cities;
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
