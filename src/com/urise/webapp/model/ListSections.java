package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSections extends AbstractSections {

    private final List<String> items;

    public ListSections(List<String> items) {
        Objects.requireNonNull(items , "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSections that = (ListSections) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return "\n" + items +
                '}';
    }
}

