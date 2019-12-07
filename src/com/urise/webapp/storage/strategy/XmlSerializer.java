package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializer implements SerializerStrategy {

    private XmlParser xmlParser;

    public XmlSerializer() {
        xmlParser = new XmlParser(Resume.class, LineSection.class, ListSection.class, PlaceSection.class,
                Place.class, Place.PlaceDescription.class, PlaceLink.class);
    }

    @Override
    public Resume readResume(InputStream in) throws IOException {
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }

    @Override
    public void writeResume(Resume resume, OutputStream out) throws IOException {
        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }
}
