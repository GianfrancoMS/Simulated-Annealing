package com.gianfranco.simulatedannealing.view;

import com.gianfranco.simulatedannealing.model.Place;
import com.gianfranco.simulatedannealing.presenter.Summary;

import java.util.List;

public interface MainView {
    void drawPlaces(List<Place> places);

    void focusMapOn(Place place);

    void drawRoute(List<Place> places);

    void clearMap();

    void enableUserInput();

    void disableUserInput();

    void showError(String message);

    void showSummary(Summary summary);
}
