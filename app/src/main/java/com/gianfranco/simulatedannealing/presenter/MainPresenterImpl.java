package com.gianfranco.simulatedannealing.presenter;

import com.gianfranco.simulatedannealing.model.Place;
import com.gianfranco.simulatedannealing.model.Point;
import com.gianfranco.simulatedannealing.view.MainView;

import java.util.Arrays;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private MainView view;

    private final List<Place> places = Arrays.asList(
            new Place(new Point(-16.3445345, -71.5679539), "Rodriguez Ballon"),
            new Place(new Point(-13.713229, -73.352518), "Andahuaylas"),
            new Place(new Point(-9.347222, -77.598333), "Comandante FAP Germán Arias Graciani"),
            new Place(new Point(-13.1535905, -74.2064698), "Coronel FAP Alfredo Mendívil"),
            new Place(new Point(-7.1373045, -78.490404), "Mayor Gral. FAP Armando Revoredo"),
            new Place(new Point(-9.14927, -78.5250945), "Tnte. FAP Jaime De Montruil M.")
    );

    private final Place peru = new Place(new Point(-9.2155836, -79.5154788), "Peru");

    @Override
    public void onAttach(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        if (view != null) {
            view = null;
        }
    }

    @Override
    public void onLoadPlaces() {
        if (view != null) {
            view.focusMapOn(peru);
            view.drawPlaces(places);
            view.drawRoute(places);
        }
    }
}
