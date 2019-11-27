package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSections extends AbstractSections {

    private final List<String> data;

    public ListSections(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSections that = (ListSections) o;
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

