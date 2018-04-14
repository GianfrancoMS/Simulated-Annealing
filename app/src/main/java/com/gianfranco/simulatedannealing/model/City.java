package com.gianfranco.simulatedannealing.model;

import java.util.Objects;

public final class City {

    private final Point location;
    private final String name;

    public City(Point location, String name) {
        this.location = location;
        this.name = name;
    }

    public City(City city) {
        this.location = new Point(city.location);
        this.name = city.name;
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
        City city = (City) o;
        return Objects.equals(location, city.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "City{" +
                "location=" + location +
                ", name='" + name + '\'' +
                '}';
    }
}
