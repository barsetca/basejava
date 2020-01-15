package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.TestData.*;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    //     new File("C:/Users/HP/IdeaProjects/basejava/lesson/storage");
    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUpTest() {
        storage.clear();
        storage.save(resume3);
        storage.save(resume1);
        storage.save(resume2);
    }

    @Test
    public void sizeTest() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void updateTest() {
        System.out.println(resume3);
        Resume newResume = new Resume(UUID_3, "New Name");
        newResume.setContact(ContactType.MOBIL, "999");
        newResume.setContact(ContactType.E_MAIL, "999@999");
        newResume.setSection(SectionType.OBJECTIVE, new LineSection("strelok"));
        newResume.setSection(SectionType.PERSONAL, new LineSection("metkiy"));
        newResume.setSection(SectionType.ACHIEVEMENT, new ListSection("10 celey", "5 missiy"));
        newResume.setSection(SectionType.QUALIFICATION, new ListSection("profi", "super"));
        newResume.setSection(SectionType.EXPERIENCE,
                new PlaceSection(Arrays.asList(
                        new Place(new PlaceLink("name", "url"),
                                Arrays.asList(
                                        new Place.PlaceDescription(
                                                DateUtil.of(1990, Month.DECEMBER),
                                                DateUtil.of(1995, Month.DECEMBER),
                                                "experienceTitle",
                                                ""))))));
        newResume.setSection(SectionType.EDUCATION,
                new PlaceSection(Arrays.asList(
                        new Place("educationName.get(count)", "",
                                new Place.PlaceDescription(
                                        DateUtil.of(1990, Month.DECEMBER),
                                        DateUtil.of(1995, Month.DECEMBER),
                                        "educationTitle",
                                        "descriptionNew")))));
        storage.update(newResume);
        System.out.println(newResume);
        assertEquals(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(resume4);
    }

    @Test
    public void getAllSortedTest() {
        List<Resume> testStorage = storage.getAllSorted();
        List<Resume> storageArray = Arrays.asList(resume1, resume2, resume3);
        assertEquals(3, testStorage.size());
        assertEquals(storageArray, testStorage);
    }

    @Test
    public void saveTest() {
        storage.save(resume4);
        assertEquals(resume4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistTest() {
        storage.save(resume3);
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void getTest() {
        assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get(UUID_4);
    }
}

