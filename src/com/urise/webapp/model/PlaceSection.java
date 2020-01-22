package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlaceSection extends AbstractSections {
    private static final long serialVersionUID = 1L;

    private List<Place> places;

    public PlaceSection() {
    }

    public PlaceSection(Place... places) {
        this(Arrays.asList(places));
    }

    public PlaceSection(List<Place> places) {
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
        PlaceSection that = (PlaceSection) o;
        return places.equals(that.places);
    }

    @Override
    public int hashCode() {
        return Objects.hash(places);
    }

    @Override
    public String toString() {
        return "" + places;
    }
}
