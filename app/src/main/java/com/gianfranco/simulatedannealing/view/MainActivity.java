package com.gianfranco.simulatedannealing.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
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
    public void drawRoute(List<Place> places) {
        int arrowColor = Color.BLUE;
        int lineColor = Color.BLUE;

        BitmapDescriptor endCapIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_arrow_upward);

        PolylineOptions options = new PolylineOptions()
                .width(8)
                .color(lineColor)
                .geodesic(true)
                .startCap(new RoundCap())
                .endCap(new CustomCap(endCapIcon, 8))
                .jointType(JointType.ROUND);

        List<LatLng> points = new ArrayList<>();

        for (int i = 0, size = places.size(); i < size; ++i) {
            Place place = places.get(i);
            LatLng point = new LatLng(place.x(), place.y());
            points.add(point);
        }

        Place first = places.get(0);
        points.add(new LatLng(first.x(), first.y()));

        options.addAll(points);

        googleMap.addPolyline(options);
    }

    @Override
    public void focusMapOnPlace(Place place) {
        LatLng position = new LatLng(place.x(), place.y());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

}
