package com.urise.webapp.util;

import com.urise.webapp.model.*;

import java.time.LocalDate;
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
                return contactType.getTitle() + ": " + "<a href=" + value + "target=\"_blank\">" + value + "</a>";
            default:
                throw new IllegalStateException("Unexpected value: " + contactType);
        }
    }

    public static String toHtmlSection(SectionType sectionType, AbstractSections abstractSections) {
        String s = abstractSections.toString();
        String type = "<h3>" + sectionType.getTitle() + "</h3>";

        if (s.equals("null") || s.equals("")) {
            return "";
        }
        switch (sectionType.name()) {
            case "OBJECTIVE":
            case "PERSONAL":
                return type + "<ul><li>" + abstractSections.toString() + "</li></ul>";
            case "ACHIEVEMENT":
            case "QUALIFICATION":
                ListSection listSection = (ListSection) abstractSections;
                StringJoiner joinerList = new StringJoiner("");

                for (String string : listSection.getItems()) {
                    if (string.equals("")) {
                        continue;
                    }
                    joinerList.add("<li>" + string.trim() + "</li>");

                }
                return type + "<ul>" + joinerList.toString() + "</ul>";

            case "EXPERIENCE":
            case "EDUCATION":
                PlaceSection placeSection = (PlaceSection) abstractSections;
                if (placeSection.getPlaces().size() == 0) {
                    return "";
                }
                StringJoiner joinerPlace = new StringJoiner("<br/>");
                List<Place> listPlaces = placeSection.getPlaces();

                for (Place place : listPlaces) {
                    joinerPlace.add("<li><b>" + place.getLink().getName() + "</b> " +
                            "<a href=" + place.getLink().getUrl() + ">" + place.getLink().getUrl() + "</a></li>");
                    List<Place.PlaceDescription> placeDescriptions = place.getListDescriptions();

                    for (Place.PlaceDescription description : placeDescriptions) {
                        String startDate = description.getStartDate().isAfter(LocalDate.now()) ? "н/д" :
                                DateUtil.localDateToString(description.getStartDate());

                        String endDate = description.getEndDate().isAfter(LocalDate.now()) ? "по настоящее время" :
                                DateUtil.localDateToString(description.getEndDate());

                        joinerPlace.add(startDate + " - " + endDate + "    " + "<b>" + description.getTitle() + "</b>");
                        joinerPlace.add(description.getDescription() + "<br/>");
                    }
                }
                return type + "<ul>" + joinerPlace.toString() + "</ul>";
            default:
                throw new IllegalStateException("Unexpected value: " + sectionType);
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
