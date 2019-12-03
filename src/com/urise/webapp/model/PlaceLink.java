package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class PlaceLink implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String url;

    public PlaceLink(String name, String url) {
        Objects.requireNonNull(name , "name must not be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceLink link = (PlaceLink) o;
        return name.equals(link.name) &&
                Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return  "name = " + name + '\'' +
                ", url = " + url + '\'' +
                '}';
    }
}
