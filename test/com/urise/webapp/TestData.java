package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.UUID;

public class TestData {
    public static final Resume resume1;
    public static final Resume resume2;
    public static final Resume resume3;
    public static final Resume resume4;

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    static {
        resume1 = ResumeTestData.createResume(UUID_1, "fullName_1");
        resume2 = ResumeTestData.createResume(UUID_2, "fullName_2");
        resume3 = ResumeTestData.createResume(UUID_3, "fullName_3");
        resume4 = ResumeTestData.createResume(UUID_4, "fullName_4");
    }
}
