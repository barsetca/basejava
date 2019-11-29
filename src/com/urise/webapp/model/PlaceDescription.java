package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class PlaceDescription {
    private final LocalDate dateStart;
    private final LocalDate dateFinish;
    private final String title;
    private final String description;

    public PlaceDescription(LocalDate dateStart, LocalDate dateFinish, String title, String description) {
        Objects.requireNonNull(dateStart, "dateStart must not be null");
        Objects.requireNonNull(dateFinish , "dateFinish must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceDescription that = (PlaceDescription) o;
        return dateStart.equals(that.dateStart) &&
                dateFinish.equals(that.dateFinish) &&
                title.equals(that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateStart, dateFinish, title, description);
    }

    @Override
    public String toString() {
        return  "\ndateStart=" + dateStart +
                ",dateFinish=" + dateFinish +
                ",\ntitle='" + title + '\'' +
                ",\ndescription='" + description + '\'';
    }
}
