package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    public static final Resume EMPTY = new Resume("");

    static {
        EMPTY.setSection(SectionType.OBJECTIVE, LineSection.EMPTY);
        EMPTY.setSection(SectionType.PERSONAL, LineSection.EMPTY);
        EMPTY.setSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.setSection(SectionType.QUALIFICATION, ListSection.EMPTY);
        EMPTY.setSection(SectionType.EXPERIENCE, new PlaceSection(Place.EMPTY));
        EMPTY.setSection(SectionType.EDUCATION, new PlaceSection(Place.EMPTY));
    }

    // Unique identifier
    private String uuid;
    private String fullName;
    private final Map<SectionType, AbstractSections> sectionsMap = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contactsMap = new EnumMap<>(ContactType.class);

    public Resume() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public Map<SectionType, AbstractSections> getSectionsMap() {
        return sectionsMap;
    }

    public Map<ContactType, String> getContactsMap() {
        return contactsMap;
    }

    public AbstractSections getSection(SectionType sectionType) {
        return sectionsMap.get(sectionType);
    }

    public void setSection(SectionType sectionType, AbstractSections section) {
        sectionsMap.put(sectionType, section);
    }

    public String getContact(ContactType contactType) {
        return contactsMap.get(contactType);
    }

    public void setContact(ContactType type, String data) {
        contactsMap.put(type, data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sectionsMap, resume.sectionsMap) &&
                Objects.equals(contactsMap, resume.contactsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sectionsMap, contactsMap);
    }

    @Override
    public int compareTo(Resume o) {
        return fullName.compareTo(o.fullName) == 0 ? uuid.compareTo(o.uuid) : fullName.compareTo(o.fullName);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "\nuuid='" + uuid + '\'' +
                ",\nfullName='" + fullName + '\'' +
                ",\ncontactsMap=" + contactsMap +
                ",\nsectionsMap=" + sectionsMap +
                '}';
    }
}
