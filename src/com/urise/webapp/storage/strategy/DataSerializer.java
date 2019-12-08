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
                String sectionType = dataIn.readUTF();
                switch (sectionType) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        resume.setSection(SectionType.valueOf(sectionType), new LineSection(dataIn.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        resume.setSection(SectionType.valueOf(sectionType), new ListSection(readListSection(dataIn)));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        resume.setSection(SectionType.valueOf(sectionType), new PlaceSection(readPlaceSection(dataIn)));
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
            dataOut.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
                dataOut.writeUTF(pair.getKey().name());
                dataOut.writeUTF(pair.getValue());
            }
            Map<SectionType, AbstractSections> sections = resume.getSectionsMap();
            dataOut.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSections> pair : sections.entrySet()) {
                dataOut.writeUTF(pair.getKey().name());
                switch (pair.getKey().name()) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        LineSection lineSection = (LineSection) pair.getValue();
                        dataOut.writeUTF(lineSection.getText());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        ListSection listSection = (ListSection) pair.getValue();
                        writeListSection(listSection.getItems(), dataOut);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        PlaceSection placeSection = (PlaceSection) pair.getValue();
                        writePlaceSection(placeSection.getPlaces(), dataOut);
                        break;
                }
            }
        }
    }

    private void writeListSection(List<String> list, DataOutputStream dataOut) throws IOException {
        dataOut.writeInt(list.size());
        for (String s : list) {
            dataOut.writeUTF(s);
        }
    }

    private void writePlaceSection(List<Place> places, DataOutputStream dataOut) throws IOException {
        dataOut.writeInt(places.size());
        for (Place place : places) {
            dataOut.writeUTF(place.getLink().getName());
            writeCheckNull(place.getLink().getUrl(), dataOut);
            dataOut.writeInt(place.getListDescriptions().size());
            for (Place.PlaceDescription placeDescription : place.getListDescriptions()) {
                writeStartEndDate(placeDescription.getStartDate(), placeDescription.getEndDate(), dataOut);
                dataOut.writeUTF(placeDescription.getTitle());
                writeCheckNull(placeDescription.getDescription(), dataOut);
            }
        }
    }

    private void writeStartEndDate(LocalDate startDate, LocalDate endDate, DataOutputStream dataOut) throws IOException {
        dataOut.writeUTF(DateUtil.localDateToString(startDate));
        dataOut.writeUTF(DateUtil.localDateToString(endDate));
    }

    private void writeCheckNull(String string, DataOutputStream dataOut) throws IOException {
        if (string == null) {
            dataOut.writeUTF("");
        } else dataOut.writeUTF(string);

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
            int sizeDescription = dataIn.readInt();
            List<Place.PlaceDescription> placeDescriptions = new ArrayList<>();
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
}
