package com.gianfranco.simulatedannealing.presenter;

import com.gianfranco.simulatedannealing.view.MainView;

public interface MainPresenter {
    void onAttach(MainView view);

    void onDetach();

    void onLoadPlaces();
}
