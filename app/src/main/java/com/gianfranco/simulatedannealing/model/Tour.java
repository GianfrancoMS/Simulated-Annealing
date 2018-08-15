package com.gianfranco.simulatedannealing.model;

import java.util.ArrayList;
import java.util.List;

public final class Tour {
    private List<Place> places;
    private final DistanceCalculator calculator;

    public Tour(List<Place> places, DistanceCalculator calculator) {
        this.places = checkValidPlaces(places);
        this.calculator = calculator;
    }

    Tour(Tour tour) {
        this.places = checkValidPlaces(tour.places);
        this.calculator = tour.calculator;
    }

    public List<Place> places() {
        return deepCopy(places);
    }

    public double distance() {
        int numberOfPlaces = places.size();

        double distance = 0;

        Place first = places.get(0);
        Place last = places.get(numberOfPlaces - 1);

        for (int i = 0; i < numberOfPlaces - 1; ++i) {
            Place current = places.get(i);
            Place next = places.get(i + 1);
            distance += calculator.distanceBetween(current, next);
        }

        distance += calculator.distanceBetween(last, first);

        return distance;
    }

    DistanceCalculator calculator() {
        return calculator;
    }

    private List<Place> deepCopy(List<Place> places) {
        int size = places.size();
        List<Place> copy = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            Place place = new Place(places.get(i));
            copy.add(place);
        }
        return copy;
    }

    private List<Place> checkValidPlaces(List<Place> places) {
        if (places == null) {
            throw new NullPointerException("places = null");
        } else if (places.isEmpty()) {
            throw new IllegalArgumentException("There are no places for the tour");
        } else if (places.size() <= 2) {
            throw new IllegalArgumentException("Tour needs at least 3 places");
        } else {
            return deepCopy(places);
        }
    }
}
