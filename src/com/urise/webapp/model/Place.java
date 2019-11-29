package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Place {


    private final PlaceLink link;
    private final List<PlaceDescription> descriptions;


    public Place(String name, String url, List<PlaceDescription> descriptions) {
        this.link = new PlaceLink(name, url);
        this.descriptions = descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return link.equals(place.link) &&
                descriptions.equals(place.descriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, descriptions);
    }

    @Override
    public String toString() {
        return "Place{" +
                "link=" + link +
                ",\ndescriptions=" + descriptions +
                '}';
    }
}
