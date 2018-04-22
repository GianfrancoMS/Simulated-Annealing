package com.gianfranco.simulatedannealing.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public final class SimulatedAnnealing {

    private final double temperature;
    private final double coolingSpeed;
    private final double lowerBound;

    public SimulatedAnnealing(double temperature, double coolingSpeed, double lowerBound) {
        this.temperature = checkValidTemperature(temperature);
        this.coolingSpeed = checkValidCoolingSpeed(coolingSpeed);
        this.lowerBound = checkValidLowerBound(lowerBound);
    }

    public Flowable<Result> optimize(final Tour tour) {
        return Flowable.create(emitter -> {

            double temperature = this.temperature;

            Tour currentTour = new Tour(tour);

            final DistanceCalculator calculator = currentTour.calculator();

            List<Place> copyOfPlaces = currentTour.places();
            Collections.shuffle(copyOfPlaces);
            currentTour = new Tour(copyOfPlaces, calculator);

            Tour bestTour = new Tour(currentTour);

            final Random rand = new Random();
            final int maxValue = currentTour.places().size();

            int iterations = 0;
            int replacements = 0;

            if (!emitter.isCancelled()) {
                emitter.onNext(new Result(bestTour.places(), iterations, replacements, temperature, bestTour.distance()));
            }

            boolean hasChanged;

            double bestDistance = 0;

            while (temperature >= lowerBound) {
                ++iterations;

                final int i = rand.nextInt(maxValue);
                int j;
                do {
                    j = rand.nextInt(maxValue);
                } while (i == j);

                List<Place> newPlaces = currentTour.places();
                swapCitiesPositions(newPlaces, i, j);
                Tour newTour = new Tour(newPlaces, calculator);

                final double acceptanceProbability = acceptanceProbability(currentTour, newTour);
                final double random = rand.nextDouble();

                if (acceptanceProbability > random) {
                    currentTour = new Tour(newTour);
                }

                bestDistance = currentTour.distance();
                if (bestDistance < bestTour.distance()) {
                    bestTour = new Tour(currentTour);
                    ++replacements;
                    hasChanged = true;
                } else {
                    hasChanged = false;
                }

                if (hasChanged && !emitter.isCancelled()) {
                    emitter.onNext(new Result(bestTour.places(), iterations, replacements, temperature, bestDistance));
                }

                temperature *= (1 - coolingSpeed);
            }

            if (!emitter.isCancelled()) {
                emitter.onNext(new Result(bestTour.places(), iterations, replacements, temperature, bestDistance));
            }

            emitter.onComplete();

        }, BackpressureStrategy.BUFFER);
    }

    private double acceptanceProbability(Tour current, Tour next) {
        if (next.distance() < current.distance()) {
            return 1;
        }
        return Math.exp((current.distance() - next.distance()) / temperature);
    }

    private void swapCitiesPositions(List<Place> places, int i, int j) {
        Collections.swap(places, i, j);
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
