package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    private final PlaceLink link;
    private final List<PlaceDescription> descriptions;

    public Place(String name, String url, PlaceDescription... descriptions) {
        this(new PlaceLink(name, url), Arrays.asList(descriptions));
    }

    public Place(PlaceLink link, List<PlaceDescription> descriptions) {
        this.link = link;
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
        return "Place(" + link + "\n" + descriptions + ')';
    }

    public static class PlaceDescription implements Serializable {
        private static final long serialVersionUID = 1L;

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public PlaceDescription(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public PlaceDescription(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public PlaceDescription(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlaceDescription that = (PlaceDescription) o;
            return startDate.equals(that.startDate) &&
                    endDate.equals(that.endDate) &&
                    title.equals(that.title) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "" + "\n" + startDate + ", " + endDate + ", " + title + ", " + description + ')';
        }
    }
}
