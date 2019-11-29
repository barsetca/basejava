package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class PlaceSections extends AbstractSections {

    private final List<Place> places;

    public PlaceSections(List<Place> places) {
        Objects.requireNonNull(places, "places must not be null");
        this.places = places;
    }

    public List<Place> getPlaces() {
        return places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceSections that = (PlaceSections) o;
        return places.equals(that.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(places);
    }

    @Override
    public String toString() {
        return "\n" + places +
                '}';
    }
}
