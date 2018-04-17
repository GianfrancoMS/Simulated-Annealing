package com.gianfranco.simulatedannealing.view;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.gianfranco.simulatedannealing.R;
import com.gianfranco.simulatedannealing.model.Place;
import com.gianfranco.simulatedannealing.model.Point;
import com.gianfranco.simulatedannealing.presenter.MainPresenter;
import com.gianfranco.simulatedannealing.presenter.MainPresenterImpl;
import com.gianfranco.simulatedannealing.presenter.Summary;
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
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl();
        presenter.onAttach(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(v -> presenter.startOptimization());
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
        googleMap.setOnMapClickListener(latLng -> {
            String title = latLng.latitude + " : " + latLng.longitude;

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);
            googleMap.addMarker(markerOptions);

            Place place = new Place(new Point(latLng.latitude, latLng.longitude), title);
            presenter.addNewPlace(place);
        });
        presenter.onMapReady();
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
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_arrow_upward_white))
                    .title(currentPlace.name())
            );
        }

        Place lastPlace = places.get(size - 1);
        LatLng lastLatLng = from(lastPlace);

        Place firstPlace = places.get(0);
        LatLng firstLatLng = from(firstPlace);

        Double rotation = SphericalUtil.computeHeading(lastLatLng, firstLatLng);

        googleMap.addMarker(new MarkerOptions()
                .position(lastLatLng)
                .rotation(rotation.floatValue())
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_arrow_upward_black))
                .title(lastPlace.name())
        );
    }

    @Override
    public void focusMapOn(Place place) {
        LatLng position = from(place);
        final float zoom = 5;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
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
    public void clearMap() {
        if (route != null) {
            route.remove();
            googleMap.clear();
        }
    }

    @Override
    public void enableUserInput() {
        startButton.setEnabled(true);
    }

    @Override
    public void disableUserInput() {
        startButton.setEnabled(false);
    }

    @Override
    public void showError(String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("An error has occurred");
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", (dialog, id) -> {
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showSummary(Summary summary) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Optimization completed");
        builder.setMessage(
                "Iterations: " + summary.iterations() + "\n" +
                        "Replacements: " + summary.replacements() + "\n" +
                        "Final temperature: " + summary.temperature() + "\n" +
                        "Final distance: " + summary.distance() + " km"
        );
        builder.setCancelable(false);

        builder.setPositiveButton("OK", (dialog, id) -> {
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private LatLng from(Place place) {
        return new LatLng(place.x(), place.y());
    }

}
