package com.gianfranco.simulatedannealing.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gianfranco.simulatedannealing.R;
import com.gianfranco.simulatedannealing.model.Place;
import com.gianfranco.simulatedannealing.presenter.MainPresenter;
import com.gianfranco.simulatedannealing.presenter.MainPresenterImpl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MainView {

    private GoogleMap googleMap;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl();
        presenter.onAttach(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        presenter.onLoadPlaces();
    }

    @Override
    public void drawPlaces(List<Place> places) {
        for (int i = 0, length = places.size(); i < length; ++i) {
            Place place = places.get(i);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(place.x(), place.y()))
                    .title(place.name()));
        }
    }

    @Override
    public void focusMapOnPlace(Place place) {
        LatLng position = new LatLng(place.x(), place.y());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
