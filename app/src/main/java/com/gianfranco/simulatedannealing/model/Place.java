package com.gianfranco.simulatedannealing.model;

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
}
