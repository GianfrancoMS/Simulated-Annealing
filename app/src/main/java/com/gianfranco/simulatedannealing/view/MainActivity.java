package com.gianfranco.simulatedannealing.view;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MainView {

    private GoogleMap googleMap;
    private MainPresenter presenter;
    private Polyline route;

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
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        presenter.onLoadPlaces();
    }

    @Override
    public void drawPlaces(List<Place> places) {
        int size = places.size();

        for (int i = 0; i < size - 1; ++i) {
            Place currentPlace = places.get(i);
            Place nextPlace = places.get(i + 1);

            LatLng currentLatLng = from(currentPlace);
            LatLng nextLatLng = from(nextPlace);

            Double rotation = SphericalUtil.computeHeading(currentLatLng, nextLatLng);

            googleMap.addMarker(new MarkerOptions()
                    .position(currentLatLng)
                    .rotation(rotation.floatValue())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_arrow_upward))
                    .title(currentPlace.name()));
        }

        Place lastPlace = places.get(size - 1);
        LatLng lastLatLng = from(lastPlace);

        Place firstPlace = places.get(0);
        LatLng firstLatLng = from(firstPlace);

        Double rotation = SphericalUtil.computeHeading(lastLatLng, firstLatLng);

        googleMap.addMarker(new MarkerOptions()
                .position(lastLatLng)
                .rotation(rotation.floatValue())
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_arrow_upward))
                .title(lastPlace.name()));
    }

    @Override
    public void focusMapOn(Place place) {
        LatLng position = from(place);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    @Override
    public void drawRoute(List<Place> places) {
        PolylineOptions polyLine = new PolylineOptions().width(8).color(Color.BLACK).geodesic(true);
        for (int i = 0, size = places.size(); i < size; ++i) {
            Place place = places.get(i);
            LatLng point = from(place);
            polyLine.add(point);
        }

        Place first = places.get(0);
        polyLine.add(from(first));

        route = googleMap.addPolyline(polyLine);
    }

    @Override
    public void clearRoute() {
        route.remove();
    }

    private LatLng from(Place place) {
        return new LatLng(place.x(), place.y());
    }
}
