package com.gianfranco.simulatedannealing.view;

import com.gianfranco.simulatedannealing.model.Place;

import java.util.List;

public interface MainView {
    void drawPlaces(List<Place> places);

    void focusMapOn(Place place);

    void drawRoute(List<Place> places);

    void clearRoute();
}
