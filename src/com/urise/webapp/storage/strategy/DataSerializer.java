package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
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
            int size = dataIn.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dataIn.readUTF()), dataIn.readUTF());
            }
            resume.setSection(SectionType.valueOf(dataIn.readUTF()), new LineSection(dataIn.readUTF()));
            resume.setSection(SectionType.valueOf(dataIn.readUTF()), new LineSection(dataIn.readUTF()));

            resume.setSection(SectionType.valueOf(dataIn.readUTF()), new ListSection(readListSection(dataIn)));
            resume.setSection(SectionType.valueOf(dataIn.readUTF()), new ListSection(readListSection(dataIn)));

            resume.setSection(SectionType.valueOf(dataIn.readUTF()), new PlaceSection(readPlaceSection(dataIn)));
            resume.setSection(SectionType.valueOf(dataIn.readUTF()), new PlaceSection(readPlaceSection(dataIn)));

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
            LineSection objective = (LineSection) resume.getSection(SectionType.OBJECTIVE);
            LineSection personal = (LineSection) resume.getSection(SectionType.PERSONAL);
            ListSection achievement = (ListSection) resume.getSection(SectionType.ACHIEVEMENT);
            ListSection qualification = (ListSection) resume.getSection(SectionType.QUALIFICATION);
            PlaceSection experience = (PlaceSection) resume.getSection(SectionType.EXPERIENCE);
            PlaceSection education = (PlaceSection) resume.getSection(SectionType.EDUCATION);

            dataOut.writeUTF(SectionType.OBJECTIVE.name());
            dataOut.writeUTF(objective.getText());

            dataOut.writeUTF(SectionType.PERSONAL.name());
            dataOut.writeUTF(personal.getText());

            dataOut.writeUTF(SectionType.ACHIEVEMENT.name());
            writeListSection(achievement.getItems(), dataOut);

            dataOut.writeUTF(SectionType.QUALIFICATION.name());
            writeListSection(qualification.getItems(), dataOut);

            dataOut.writeUTF(SectionType.EXPERIENCE.name());
            writePlaceSection(experience.getPlaces(), dataOut);

            dataOut.writeUTF(SectionType.EDUCATION.name());
            writePlaceSection(education.getPlaces(), dataOut);
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
                dataOut.writeUTF(DateUtil.localDateToString(placeDescription.getStartDate()));
                dataOut.writeUTF(DateUtil.localDateToString(placeDescription.getEndDate()));
                dataOut.writeUTF(placeDescription.getTitle());
                writeCheckNull(placeDescription.getDescription(), dataOut);
            }
        }
    }

    private void writeCheckNull(String string, DataOutputStream dataOut) throws IOException {
        if (string == null || string.equals("")) {
            dataOut.writeInt(0);
        } else {
            dataOut.writeInt(1);
            dataOut.writeUTF(string);
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
            String name = dataIn.readUTF();
            int checkNull = dataIn.readInt();
            String url = readCheckNull(checkNull, dataIn);
            PlaceLink placeLink = new PlaceLink(name, url);
            int sizeDescription = dataIn.readInt();
            List<Place.PlaceDescription> placeDescriptions = new ArrayList<>();
            for (int j = 0; j < sizeDescription; j++) {
                Place.PlaceDescription description = new Place.PlaceDescription(
                        DateUtil.stringToLocalDate(dataIn.readUTF()),
                        DateUtil.stringToLocalDate(dataIn.readUTF()),
                        dataIn.readUTF(),
                        readCheckNull(dataIn.readInt(), dataIn));
                placeDescriptions.add(description);
            }
            Place place = new Place(placeLink, placeDescriptions);
            places.add(place);
        }
        return places;
    }

    private String readCheckNull(int checkNull, DataInputStream dataIn) throws IOException {
        return checkNull == 0 ? null : dataIn.readUTF();
    }
}
