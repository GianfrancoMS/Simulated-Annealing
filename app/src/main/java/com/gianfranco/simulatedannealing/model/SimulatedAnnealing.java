package com.gianfranco.simulatedannealing.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class SimulatedAnnealing {

    private double temperature;
    private final double coolingSpeed;
    private final double lowerBound;

    public SimulatedAnnealing(double temperature, double coolingSpeed, double lowerBound) {
        this.temperature = checkValidTemperature(temperature);
        this.coolingSpeed = checkValidCoolingSpeed(coolingSpeed);
        this.lowerBound = checkValidLowerBound(lowerBound);
    }

    public Tour simulate(List<City> cities) {
        Tour currentTour = new Tour(cities);

        List<City> citiesCopy = currentTour.cities();
        Collections.shuffle(citiesCopy);
        currentTour = new Tour(citiesCopy);

        Tour bestTour = new Tour(currentTour);

        Random rand = new Random();
        int maxValue = currentTour.cities().size();

        while (temperature >= lowerBound) {
            int i = rand.nextInt(maxValue);
            int j;
            do {
                j = rand.nextInt(maxValue);
            } while (i == j);

            List<City> newCities = currentTour.cities();
            swapCitiesPositions(newCities, i, j);
            Tour newTour = new Tour(newCities);

            double acceptanceProbability = acceptanceProbability(currentTour, newTour);
            double random = rand.nextDouble();
            if (acceptanceProbability > random) {
                currentTour = new Tour(newTour);
            }

            if (currentTour.distance() < bestTour.distance()) {
                bestTour = new Tour(currentTour);
            }

            temperature *= (1 - coolingSpeed);
        }

        return new Tour(bestTour);
    }

    public double temperature() {
        return temperature;
    }

    private double acceptanceProbability(Tour current, Tour next) {
        if (next.distance() < current.distance()) {
            return 1;
        }
        return Math.exp((current.distance() - next.distance()) / temperature);
    }

    private void swapCitiesPositions(List<City> cities, int i, int j) {
        Collections.swap(cities, i, j);
    }

    private double checkValidTemperature(double temperature) {
        final double MIN_TEMPERATURE = 999;
        if (temperature <= MIN_TEMPERATURE) {
            throw new IllegalArgumentException(String.format("Temperature too low (must be higher than %f). temperature = %f", MIN_TEMPERATURE, temperature));
        } else {
            return temperature;
        }
    }

    private double checkValidCoolingSpeed(double coolingSpeed) {
        final double MAX_COOLING_SPEED = 1;
        if (coolingSpeed >= MAX_COOLING_SPEED) {
            throw new IllegalArgumentException(String.format("Cooling speed is too high (must be lower than %f). coolingSpeed = %f", MAX_COOLING_SPEED, coolingSpeed));
        } else {
            return coolingSpeed;
        }
    }

    private double checkValidLowerBound(double lowerBound) {
        final double MAX_LOWER_BOUND = 1;
        if (lowerBound >= MAX_LOWER_BOUND) {
            throw new IllegalArgumentException(String.format("Lower bound is too high (must be lower than %f). lowerBound= %f", MAX_LOWER_BOUND, lowerBound));
        } else {
            return lowerBound;
        }
    }
}
