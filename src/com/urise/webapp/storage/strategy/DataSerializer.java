package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.Place.PlaceDescription;
import static com.urise.webapp.util.DateUtil.localDateToString;

public class DataSerializer implements SerializerStrategy {

    @Override
    public Resume readResume(InputStream in) throws IOException {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            String uuid = dataIn.readUTF();
            String fullName = dataIn.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dataIn, () -> resume.setContact(ContactType.valueOf(dataIn.readUTF()), dataIn.readUTF()));

            readWithException(dataIn, () -> {
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
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionTypeName);
                }
            });
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
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }
            });
        }
    }

    private void writePlaceSection(List<Place> places, DataOutputStream dataOut) throws IOException {
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
        dataOut.writeUTF(localDateToString(localDate));
    }

    private <E> void writeWithException(Collection<E> collection, DataOutputStream dataOut, WriteForEach<E> writerForEach)
            throws IOException {
        dataOut.writeInt(collection.size());
        for (E element : collection) {
            writerForEach.writerElement(element);
        }
    }

    private List<String> readListSection(DataInputStream dataIn) throws IOException {
        List<String> list = new ArrayList<>();
        readWithException(dataIn, () -> list.add(dataIn.readUTF()));
        return list;
    }

    private List<Place> readPlaceSection(DataInputStream dataIn) throws IOException {
        List<Place> places = new ArrayList<>();
        readWithException(dataIn, () -> {
            PlaceLink placeLink = new PlaceLink(dataIn.readUTF(), dataIn.readUTF());
            List<PlaceDescription> placeDescriptions = new ArrayList<>();
            readWithException(dataIn, () -> {
                LocalDate startDate = readDate(dataIn.readUTF());
                LocalDate endDate = readDate(dataIn.readUTF());
                PlaceDescription description = new PlaceDescription(startDate, endDate, dataIn.readUTF(), dataIn.readUTF());
                placeDescriptions.add(description);
            });
            Place place = new Place(placeLink, placeDescriptions);
            places.add(place);
        });
        return places;
    }

    private LocalDate readDate(String localDate) {
        return DateUtil.stringToLocalDate(localDate);
    }

    private void readWithException(DataInputStream dataIn, ReadForEach current) throws IOException {
        int size = dataIn.readInt();
        for (int i = 0; i < size; i++) {
            current.readerElement();
        }
    }

    @FunctionalInterface
    private interface WriteForEach<E> {
        void writerElement(E element) throws IOException;
    }

    @FunctionalInterface
    private interface ReadForEach {
        void readerElement() throws IOException;
    }
}

