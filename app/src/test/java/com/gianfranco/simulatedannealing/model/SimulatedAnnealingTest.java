package com.gianfranco.simulatedannealing.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

import static org.junit.Assert.*;

public class SimulatedAnnealingTest {

    @Test
    public void simulate() {
        final List<City> cities = Arrays.asList(
                new City(new Point(5, 25), "Peru"),
                new City(new Point(10, 5), "Chile"),
                new City(new Point(15, 35), "Colombia"),
                new City(new Point(20, 20), "Bolivia"),
                new City(new Point(35, 40), "Venezuela"),
                new City(new Point(40, 25), "Brasil"),
                new City(new Point(30, 10), "Argentina")
        );

        final double temperature = 1000;
        final double coolingSpeed = 0.003;
        final double lowerBound = 0.95;

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(temperature, coolingSpeed, lowerBound);
        Result result = simulatedAnnealing.startOptimization(cities)
                .blockingLast();

        assertTrue(temperature > result.temperature());
    }
}