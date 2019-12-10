package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

            writeWithException(contacts.entrySet(), dataOut, element -> {
                dataOut.writeUTF(element.getKey().name());
                dataOut.writeUTF(element.getValue());
            });
            Map<SectionType, AbstractSections> sections = resume.getSectionsMap();

            writeWithException(sections.entrySet(), dataOut, element -> {
                String sectionType = element.getKey().name();
                dataOut.writeUTF(sectionType);

                AbstractSections abstractSections = element.getValue();
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        LineSection lineSection = (LineSection) abstractSections;
                        dataOut.writeUTF(lineSection.getText());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        ListSection listSection = (ListSection) abstractSections;
                        writeWithException(listSection.getItems(), dataOut, dataOut::writeUTF);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        PlaceSection placeSection = (PlaceSection) abstractSections;
                        writePlaceSection(placeSection.getPlaces(), dataOut);
                        break;
                }
            });
        }
    }

    public static void writePlaceSection(List<Place> places, DataOutputStream dataOut) throws IOException {
        writeWithException(places, dataOut, place -> {
            dataOut.writeUTF(place.getLink().getName());
            dataOut.writeUTF(place.getLink().getUrl());

            writeWithException(place.getListDescriptions(), dataOut, element -> {
                writeDate(element.getStartDate(), dataOut);
                writeDate(element.getEndDate(), dataOut);
                dataOut.writeUTF(element.getTitle());
                dataOut.writeUTF(element.getDescription());
            });
        });
    }

    private static void writeDate(LocalDate localDate, DataOutputStream dataOut) throws IOException {
        dataOut.writeUTF(DateUtil.localDateToString(localDate));
    }

    private static <E> void writeWithException(Collection<E> collection, DataOutputStream dataOut, WriteForEach<E> writerForEach)
            throws IOException {
        dataOut.writeInt(collection.size());
        for (E element : collection) {
            writerForEach.writerElement(element);
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
    private interface WriteForEach<E> {
        void writerElement(E element) throws IOException;
    }
}

