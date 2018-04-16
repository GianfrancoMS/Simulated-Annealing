package com.gianfranco.simulatedannealing.presenter;

import com.gianfranco.simulatedannealing.model.DistanceCalculator;
import com.gianfranco.simulatedannealing.model.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

final class GoogleMapsCalculator implements DistanceCalculator {

    @Override
    public double distanceBetween(Place begin, Place end) {
        LatLng beginLatLng = new LatLng(begin.x(), begin.y());
        LatLng endLatLng = new LatLng(end.x(), end.y());
        return SphericalUtil.computeDistanceBetween(beginLatLng, endLatLng);
    }
}
