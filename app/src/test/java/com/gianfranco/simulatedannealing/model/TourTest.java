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
        thrown.expectMessage("places = null");
        List<Place> places = null;
        Tour tour = new Tour(places);
    }

    @Test
    public void When_EmptyListIsPassedToTourConstructor_Expect_IllegalArgumentExceptionToBeThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("There are no places for the tour");
        Tour tour = new Tour(new ArrayList<>());
    }

    @Test
    public void When_ListWithTwoPlacesIsPassedToTourConstructor_Expect_IllegalArgumentExceptionToBeThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Tour needs at least 3 places");
        Tour tour = new Tour(Arrays.asList(
                new Place(new Point(1, 1), "First"),
                new Place(new Point(2, 2), "Second")
        ));
    }

    @Test
    public void When_GetPlacesIsCalled_Expect_DeepCopy() {
        List<Place> places = Arrays.asList(
                new Place(new Point(1, 1), "First"),
                new Place(new Point(5, 1), "Second"),
                new Place(new Point(10, 1), "Third")
        );

        Tour tour = new Tour(places);
        List<Place> deepCopy = tour.places();

        assertFalse(tour.places() == places);
        assertFalse(tour.places() == deepCopy);
        assertFalse(deepCopy == places);
    }

    @Test
    public void When_NewTourIsCreatedFromAnotherOne_And_GetCitiesIsCalled_Expect_DeepCopy() {
        List<Place> places = Arrays.asList(
                new Place(new Point(1, 1), "First"),
                new Place(new Point(5, 1), "Second"),
                new Place(new Point(10, 1), "Third")
        );

        Tour tour = new Tour(places);
        Tour newTour = new Tour(tour);

        assertFalse(tour.places() == places);
        assertFalse(newTour.places() == places);
        assertFalse(tour.places() == newTour.places());
    }
}