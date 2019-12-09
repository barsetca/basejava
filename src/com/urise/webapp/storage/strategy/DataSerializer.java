package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSerializer implements SerializerStrategy {

    @Override
    public Resume readResume(InputStream in) throws IOException {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            String uuid = dataIn.readUTF();
            String fullName = dataIn.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int sizeContactsMap = dataIn.readInt();
            for (int i = 0; i < sizeContactsMap; i++) {
                resume.setContact(ContactType.valueOf(dataIn.readUTF()), dataIn.readUTF());
            }
            int sizeSectionsMap = dataIn.readInt();
            for (int i = 0; i < sizeSectionsMap; i++) {
                String sectionTypeName = dataIn.readUTF();
                SectionType sectionType = SectionType.valueOf(sectionTypeName);
                switch (sectionTypeName) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        resume.setSection(sectionType, new LineSection(dataIn.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        resume.setSection(sectionType, new ListSection(readListSection(dataIn)));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        resume.setSection(sectionType, new PlaceSection(readPlaceSection(dataIn)));
                        break;
                }
            }
            return resume;
        }
    }

    @Override
    public void writeResume(Resume resume, OutputStream out) throws IOException {
        try (DataOutputStream dataOut = new DataOutputStream(out)) {
            dataOut.writeUTF(resume.getUuid());
            dataOut.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContactsMap();

            writeWithException(contacts, dataOut, (key, value) -> {
                dataOut.writeUTF(key.name());
                dataOut.writeUTF(value);
            });
            Map<SectionType, AbstractSections> sections = resume.getSectionsMap();

            writeWithException(sections, dataOut, (key, value) -> {
                dataOut.writeUTF(key.name());
                String sectionType = key.name();
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        LineSection lineSection = (LineSection) value;
                        dataOut.writeUTF(lineSection.getText());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        ListSection listSection = (ListSection) value;
                        writeListSection(listSection.getItems(), dataOut);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        PlaceSection placeSection = (PlaceSection) value;
                        writePlaceSection(placeSection.getPlaces(), dataOut);
                        break;
                }
            });
        }
    }

    public static void writeListSection(List<String> list, DataOutputStream dataOut) throws IOException {
        dataOut.writeInt(list.size());
        for (String s : list) {
            dataOut.writeUTF(s);
        }
    }

    public static void writePlaceSection(List<Place> places, DataOutputStream dataOut) throws IOException {
        dataOut.writeInt(places.size());
        for (Place place : places) {
            dataOut.writeUTF(place.getLink().getName());
            dataOut.writeUTF(place.getLink().getUrl());

            dataOut.writeInt(place.getListDescriptions().size());
            for (Place.PlaceDescription placeDescription : place.getListDescriptions()) {
                writeDate(placeDescription.getStartDate(), dataOut);
                writeDate(placeDescription.getEndDate(), dataOut);
                dataOut.writeUTF(placeDescription.getTitle());
                dataOut.writeUTF(placeDescription.getDescription());
            }
        }
    }

    private static void writeDate(LocalDate localDate, DataOutputStream dataOut) throws IOException {
        dataOut.writeUTF(DateUtil.localDateToString(localDate));
    }

    private static <K, V> void writeWithException(Map<K, V> maps, DataOutputStream dataOut, FunctionalForEach<K, V> writerForEach)
            throws IOException {
        dataOut.writeInt(maps.size());
        for (Map.Entry<K, V> pair : maps.entrySet()) {
            writerForEach.writeForEach(pair.getKey(), pair.getValue());
        }

    }

    private List<String> readListSection(DataInputStream dataIn) throws IOException {
        List<String> list = new ArrayList<>();
        int size = dataIn.readInt();
        for (int i = 0; i < size; i++) {
            list.add(dataIn.readUTF());
        }
        return list;
    }

    private List<Place> readPlaceSection(DataInputStream dataIn) throws IOException {
        List<Place> places = new ArrayList<>();
        int size = dataIn.readInt();
        for (int i = 0; i < size; i++) {
            PlaceLink placeLink = new PlaceLink(dataIn.readUTF(), dataIn.readUTF());

            List<Place.PlaceDescription> placeDescriptions = new ArrayList<>();
            int sizeDescription = dataIn.readInt();
            for (int j = 0; j < sizeDescription; j++) {
                Place.PlaceDescription description = new Place.PlaceDescription(
                        readLocalDate(dataIn),
                        readLocalDate(dataIn),
                        dataIn.readUTF(),
                        dataIn.readUTF());
                placeDescriptions.add(description);
            }
            Place place = new Place(placeLink, placeDescriptions);
            places.add(place);
        }
        return places;
    }

    private LocalDate readLocalDate(DataInputStream dataIn) throws IOException {
        return DateUtil.stringToLocalDate(dataIn.readUTF());
    }

    @FunctionalInterface
    private interface FunctionalForEach<K, V> {
        void writeForEach(K key, V value) throws IOException;
    }
}

