package com.gianfranco.simulatedannealing.presenter;

import com.gianfranco.simulatedannealing.model.DistanceCalculator;
import com.gianfranco.simulatedannealing.model.Place;
import com.gianfranco.simulatedannealing.model.Point;
import com.gianfranco.simulatedannealing.model.Result;
import com.gianfranco.simulatedannealing.model.SimulatedAnnealing;
import com.gianfranco.simulatedannealing.model.Tour;
import com.gianfranco.simulatedannealing.view.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;

    private final double TEMPERATURE = 1000;
    private final double COOLING_SPEED = 0.0005;
    private final double LOWER_BOUND = 0.50;
    private final SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(TEMPERATURE, COOLING_SPEED, LOWER_BOUND);

    private final DistanceCalculator calculator = new GoogleMapsCalculator();

    private final List<Place> places = new ArrayList<>();

    private final Place peru = new Place(new Point(-9.5667057, -76.158411), "Peru");

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onAttach(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        disposable.clear();
        view = null;
    }

    @Override
    public void onMapReady() {
        view.focusMapOn(peru);
    }

    @Override
    public void startOptimization() {
        Tour tour;

        try {
            tour = new Tour(places, calculator);
        } catch (Exception e) {
            view.showError(e.getMessage());
            return;
        }

        final Summary summary = new Summary();

        disposable.add(simulatedAnnealing.optimize(tour)
                .concatMap(result -> Flowable.just(result).delay(250, TimeUnit.MILLISECONDS))
                .map(result -> {
                    summary.setResult(result);
                    return result;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> view.disableUserInput())
                .subscribeWith(new ResourceSubscriber<Result>() {
                    @Override
                    public void onNext(Result result) {
                        view.clearMap();
                        view.drawPlaces(result.places());
                        view.drawRoute(result.places());
                    }

                    @Override
                    public void onError(Throwable t) {
                        view.showError(t.getMessage());
                        view.enableUserInput();
                    }

                    @Override
                    public void onComplete() {
                        view.showSummary(summary);
                        view.enableUserInput();
                    }
                })
        );
    }

    @Override
    public void addNewPlace(Place place) {
        places.add(place);
    }

}
