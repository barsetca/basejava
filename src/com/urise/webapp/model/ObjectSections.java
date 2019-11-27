package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ObjectSections extends AbstractSections {

    private final List<Place> data;

    public ObjectSections(List<Place> data) {
        this.data = data;
    }

    public List<Place> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectSections that = (ObjectSections) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "\n" + data +
                '}';
    }
}
