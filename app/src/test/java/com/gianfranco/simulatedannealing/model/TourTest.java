package com.gianfranco.simulatedannealing.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TourTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void When_NullListIsPassedToTourConstructor_Expect_NullPointerExceptionToBeThrown() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("cities = null");
        List<City> cities = null;
        Tour tour = new Tour(cities);
    }

    @Test
    public void When_EmptyListIsPassedToTourConstructor_Expect_IllegalArgumentExceptionToBeThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("There are no cities for the tour");
        Tour tour = new Tour(new ArrayList<>());
    }

    @Test
    public void When_ListWithTwoCitiesIsPassedToTourConstructor_Expect_IllegalArgumentExceptionToBeThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Tour needs at least 3 cities");
        Tour tour = new Tour(Arrays.asList(
                new City(new Point(1, 1), "First"),
                new City(new Point(2, 2), "Second")
        ));
    }

    @Test
    public void When_GetCitiesIsCalled_Expect_DeepCopy() {
        List<City> cities = Arrays.asList(
                new City(new Point(1, 1), "First"),
                new City(new Point(5, 1), "Second"),
                new City(new Point(10, 1), "Third")
        );

        Tour tour = new Tour(cities);
        List<City> deepCopy = tour.cities();

        assertFalse(tour.cities() == cities);
        assertFalse(tour.cities() == deepCopy);
        assertFalse(deepCopy == cities);
    }

    @Test
    public void When_NewTourIsCreatedFromAnotherOne_And_GetCitiesIsCalled_Expect_DeepCopy() {
        List<City> cities = Arrays.asList(
                new City(new Point(1, 1), "First"),
                new City(new Point(5, 1), "Second"),
                new City(new Point(10, 1), "Third")
        );

        Tour tour = new Tour(cities);
        Tour newTour = new Tour(tour);

        assertFalse(tour.cities() == cities);
        assertFalse(newTour.cities() == cities);
        assertFalse(tour.cities() == newTour.cities());
    }
}