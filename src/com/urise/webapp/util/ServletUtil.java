package com.urise.webapp.util;

import com.urise.webapp.model.*;

public class ServletUtil {
    public static void addPlace(SectionType sectionType, Resume resume) {

        Place place = Place.EMPTY;
        if (resume.getSection(sectionType) != null) {
            ((PlaceSection) resume.getSection(sectionType)).getPlaces().add(place);
        } else {
            resume.setSection(sectionType, new PlaceSection(place));
        }
    }

    public static void addSectionsDoGet(Resume resume, String action) {
        for (SectionType sectionType : SectionType.values()) {
            AbstractSections section = resume.getSection(sectionType);

            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        resume.setSection(sectionType, LineSection.EMPTY);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    if (section == null) {
                        resume.setSection(sectionType, ListSection.EMPTY);
                    }
                    break;
                case EXPERIENCE:
                    if ("addExperience".equals(action)) {
                        addPlace(sectionType, resume);
                        break;
                    }
                    if (section == null || ((PlaceSection) section).getPlaces().size() == 0) {
                        resume.setSection(sectionType, new PlaceSection(Place.EMPTY));
                    }
                    break;
                case EDUCATION:
                    if ("addEducation".equals(action)) {
                        addPlace(sectionType, resume);
                        break;
                    }
                    if (section == null || ((PlaceSection) section).getPlaces().size() == 0) {
                        resume.setSection(sectionType, new PlaceSection(Place.EMPTY));
                    }
                    break;
            }
        }
    }
}
