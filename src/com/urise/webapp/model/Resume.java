package com.urise.webapp.model;

import java.util.EnumMap;
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
    private final Map<SectionType, AbstractSections> sectionsMap = new EnumMap<>(SectionType.class);
    private final Map<ContactsType, String> contactsMap = new EnumMap<>(ContactsType.class);

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

    public String getUuid() {
        return uuid;
    }

    public Map<SectionType, AbstractSections> getSectionsMap() {
        return sectionsMap;
    }

    public Map<ContactsType, String> getContactsMap() {
        return contactsMap;
    }

    public AbstractSections getSectionInfo(SectionType sectionType) {
        return sectionsMap.get(sectionType);
    }

    public void setSectionInfo(SectionType sectionType, AbstractSections section) {
        sectionsMap.put(sectionType, section);
    }

    public void setContactInfo(ContactsType type, String data) {
        contactsMap.put(type, data);
    }

    public String getContactInfo(ContactsType contactType) {
        return contactsMap.get(contactType);
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
