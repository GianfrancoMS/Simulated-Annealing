package com.gianfranco.simulatedannealing.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceCalculatorTest {
    private final DistanceCalculator calculator = DistanceCalculator.PYTHAGOREAN_CALCULATOR;

    @Test
    public void When_CitiesAreInTheSamePosition_Expect_DistanceEqualsToZero() {
        Place first = new Place(new Point(1, 1), "First");
        Place last = new Place(new Point(1, 1), "Last");

        assertEquals(0, calculator.distanceBetween(first, last), 0.000001);
    }

    @Test
    public void When_CitiesAreInDifferentPositions_Expect_DistanceNotEqualsToZero() {
        Place first = new Place(new Point(0, 0), "First");
        Place last = new Place(new Point(5, 5), "Last");

        double distance = Math.sqrt(5 * 5 + 5 * 5);

        assertEquals(distance, calculator.distanceBetween(first, last), 0.000001);
    }
}