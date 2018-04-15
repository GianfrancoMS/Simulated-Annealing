package com.gianfranco.simulatedannealing.model;

import java.util.Objects;

public final class Place {

    private final Point location;
    private final String name;

    public Place(Point location, String name) {
        this.location = location;
        this.name = name;
    }

    public Place(Place place) {
        this.location = new Point(place.location);
        this.name = place.name;
    }

    public double x() {
        return location.x();
    }

    public double y() {
        return location.y();
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(location, place.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "place{" +
                "location=" + location +
                ", name='" + name + '\'' +
                '}';
    }
}
