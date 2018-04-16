package com.gianfranco.simulatedannealing.presenter;

import com.gianfranco.simulatedannealing.model.Place;
import com.gianfranco.simulatedannealing.view.MainView;

public interface MainPresenter {
    void onAttach(MainView view);

    void onDetach();

    void onMapReady();

    void startOptimization();

    void addNewPlace(Place place);
}
