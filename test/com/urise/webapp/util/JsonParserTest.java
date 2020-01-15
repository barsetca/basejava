package com.urise.webapp.util;

import com.urise.webapp.model.AbstractSections;
import com.urise.webapp.model.LineSection;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.TestData.resume1;

public class JsonParserTest {

    @Test
    public void resumeTest() {
        String json = JsonParser.write(resume1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(resume1, resume);
    }

    @Test
    public void write() {
        AbstractSections lineSections = new LineSection("Prof");
        String json = JsonParser.write(lineSections, AbstractSections.class);
        System.out.println(json);
        AbstractSections lineSectionsNew = JsonParser.read(json, AbstractSections.class);
        Assert.assertEquals(lineSections, lineSectionsNew);
    }
}