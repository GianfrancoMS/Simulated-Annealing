package com.gianfranco.simulatedannealing.model;

import java.util.ArrayList;
import java.util.List;

public final class Tour {

    private final List<City> cities;
    private final DistanceCalculator calculator = DistanceCalculator.PYTHAGOREAN_CALCULATOR;

    public Tour(List<City> cities) {
        this.cities = checkValidCities(cities);
    }

    public Tour(Tour tour) {
        this.cities = checkValidCities(tour.cities);
    }

    public List<City> cities() {
        return deepCopy(cities);
    }

    public double distance() {
        int numberOfCities = cities.size();

        double distance = 0;

        City first = cities.get(0);
        City last = cities.get(numberOfCities - 1);

        for (int i = 0; i < numberOfCities - 1; ++i) {
            City current = cities.get(i);
            City next = cities.get(i + 1);
            distance += calculator.distanceBetween(current, next);
        }

        distance += calculator.distanceBetween(last, first);

        return distance;
    }

    private List<City> deepCopy(List<City> cities) {
        int size = cities.size();
        List<City> copy = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            City city = new City(cities.get(i));
            copy.add(city);
        }
        return copy;
    }

    private List<City> checkValidCities(List<City> cities) {
        if (cities == null) {
            throw new NullPointerException("cities = null");
        } else if (cities.isEmpty()) {
            throw new IllegalArgumentException("There are no cities for the tour");
        } else if (cities.size() <= 2) {
            throw new IllegalArgumentException("Tour needs at least 3 cities");
        } else {
            return deepCopy(cities);
        }
    }

    @Override
    public String toString() {
        return "Tour{" +
                "cities=" + cities +
                '}';
    }
}
