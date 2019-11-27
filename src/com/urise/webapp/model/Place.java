package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Place {

    private final LocalDate dateStart;
    private final LocalDate dateFinish;
    private final String namePlace;
    private final String linkPlace;
    private final String title;
    private final String description;

    public Place(LocalDate dateStart, LocalDate dateFinish, String namePlace, String linkPlace, String title, String description) {
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.namePlace = namePlace;
        this.linkPlace = linkPlace;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(dateStart, place.dateStart) &&
                Objects.equals(dateFinish, place.dateFinish) &&
                Objects.equals(namePlace, place.namePlace) &&
                Objects.equals(linkPlace, place.linkPlace) &&
                Objects.equals(title, place.title) &&
                Objects.equals(description, place.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateStart, dateFinish, namePlace, linkPlace, title, description);
    }

    @Override
    public String toString() {
        return "\nPlace{" +
                "\ndateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", \nnamePlace='" + namePlace + '\'' +
                ", \nlinkPlace='" + linkPlace + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \ndescription='" + description;
    }
}
