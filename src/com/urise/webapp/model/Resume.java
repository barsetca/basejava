package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, AbstractSections> sectionsMap = new HashMap<>();
    private final Map<ContactsType, String> contactsMap = new HashMap<>();


    {
        sectionsMap.put(SectionType.OBJECTIVE, new LineData());
        sectionsMap.put(SectionType.PERSONAL, new LineData());
        sectionsMap.put(SectionType.ACHIEVEMENT, new ListData());
        sectionsMap.put(SectionType.QUALIFICATION, new ListData());
        sectionsMap.put(SectionType.EXPERIENCE, new MapData());
        sectionsMap.put(SectionType.EDUCATION, new MapData());
    }


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setDataSections(SectionType type, String... data) {
        AbstractSections section = sectionsMap.get(type);
        section.setData(data);
        sectionsMap.put(type, section);
    }

    public AbstractSections getSectionData(SectionType sectionType) {
        return sectionsMap.get(sectionType);
    }

    public void setDataContacts(ContactsType type, String data) {
        contactsMap.put(type, data);
    }

    public String getContactData(ContactsType contactType) {
        return contactsMap.get(contactType);
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume o) {
        return fullName.compareTo(o.fullName) == 0 ? uuid.compareTo(o.uuid) : fullName.compareTo(o.fullName);
    }

    @Override
    public String toString() {

        return uuid + '(' + fullName + ')';
    }
}
