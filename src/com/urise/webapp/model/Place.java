package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.XmlLocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Place implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Place EMPTY = new Place("", "", PlaceDescription.EMPTY);

    private PlaceLink link;
    private List<PlaceDescription> descriptions;

    public Place() {
    }

    public Place(String name, String url, PlaceDescription... descriptions) {
        this(new PlaceLink(name, url), Arrays.asList(descriptions));
    }

    public Place(PlaceLink link, List<PlaceDescription> descriptions) {
        this.link = link;
        this.descriptions = descriptions;
    }

    public PlaceLink getLink() {
        return link;
    }

    public List<PlaceDescription> getListDescriptions() {
        return descriptions;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PlaceDescription implements Serializable {
        private static final long serialVersionUID = 1L;

        public static final PlaceDescription EMPTY = new PlaceDescription(DateUtil.EMPTY, DateUtil.EMPTY, "", "");


        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public PlaceDescription() {
        }

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
            if (description == null) {
                this.description = "";
            } else {
                this.description = description;
            }
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
