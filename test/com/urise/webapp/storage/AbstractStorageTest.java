package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;
    private Resume resume1 = new Resume(UUID_1, A);
    private Resume resume2 = new Resume(UUID_2, B);
    private Resume resume3 = new Resume(UUID_3, C);
    private Resume resume4 = new Resume(UUID_4, D);

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private static final String D = "d";

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
        String test = "ch";
        resume3.setFullName(test);
        storage.update(resume3);
        assertEquals(resume3, storage.get(UUID_3));
        assertEquals(test, storage.get(UUID_3).getFullName());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(resume4);
        assertEquals(resume4, storage.get(UUID_4));
    }

    @Test
    public void getAllSortedTest() {
        List<Resume> testStorage = storage.getAllSorted();
        List<Resume> storageArray = new ArrayList<>(Arrays.asList(resume1, resume2, resume3));
        assertEquals(3, testStorage.size());
        assertEquals(testStorage, storageArray);
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

