package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.util.List;
import java.util.StringJoiner;

public class HtmlUtil {
    public static String toHtmlContacts(ContactType contactType, String value) {
        if (value == null) return "";
        switch (contactType.name()) {
            case "MOBIL":
            case "HOME_PHONE":
                return contactType.getTitle() + ": " + value;
            case "E_MAIL":
                return "<a href='mailto:" + value + "'>" + value + "</a>";
            case "SKYPE":
                return contactType.getTitle() + ": " + "<a href='skype:" + value + "'>" + value + "</a>";
            case "PROFILE_LINKEDIN":
            case "PROFILE_STACKOVERFLOW":
            case "HOME_PAGE":
            case "PROFILE_GITHUB":
                return contactType.getTitle() + ": " + "<a href=" + value + ">" + value + "</a>";
            default:
                throw new IllegalStateException("Unexpected value: " + contactType);
        }
    }

    public static String toHtmlSection(SectionType sectionType, AbstractSections abstractSections) {
        if (abstractSections == null) return "";
        switch (sectionType.name()) {
            case "OBJECTIVE":
            case "PERSONAL":
                return abstractSections.toString();
            case "ACHIEVEMENT":
            case "QUALIFICATION":
                ListSection listSection = (ListSection) abstractSections;
                return String.join(", ", listSection.getItems())
                        .replaceAll("}*\\{*]*\\[*", "");
            case "EXPERIENCE":
            case "EDUCATION":
                PlaceSection placeSection = (PlaceSection) abstractSections;
                StringJoiner joiner = new StringJoiner("<br/>");
                List<Place> listPlaces = placeSection.getPlaces();
                for (Place place : listPlaces) {
                    joiner.add("<b>" + place.getLink().getName() + "</b> " +
                            "<a href=" + place.getLink().getUrl() + ">" + place.getLink().getUrl() + "</a>");
                    List<Place.PlaceDescription> placeDescriptions = place.getListDescriptions();
                    for (Place.PlaceDescription description : placeDescriptions) {
                        joiner.add(DateUtil.localDateToString(description.getStartDate()) +
                                " - " + DateUtil.localDateToString(description.getEndDate()) +
                                "    " + "<b>" + description.getTitle() + "</b>");
                        joiner.add(description.getDescription());
                    }
                }
                return joiner.toString();
            default:
                throw new IllegalStateException("Unexpected value: " + sectionType);
        }
    }
}
