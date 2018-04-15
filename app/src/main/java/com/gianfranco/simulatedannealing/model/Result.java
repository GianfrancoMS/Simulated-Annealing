package com.gianfranco.simulatedannealing.model;

import java.util.List;

public final class Result {
    private final List<City> cities;
    private final int iterations;
    private final int replacements;

    public Result(List<City> cities, int iterations, int replacements) {
        this.cities = cities;
        this.iterations = iterations;
        this.replacements = replacements;
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
}
