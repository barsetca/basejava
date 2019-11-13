package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;
    private Resume resume1 = new Resume(UUID_1);
    private Resume resume2 = new Resume(UUID_2);
    private Resume resume3 = new Resume(UUID_3);
    private Resume resume4 = new Resume(UUID_4);

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume3);
        assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume4);
        assertEquals(resume4, storage.get(UUID_4));
    }

    @Test
    public void getAll() {
        Resume[] testStorage = storage.getAll();
        assertEquals(3, storage.getAll().length);
        assertEquals(testStorage[0], storage.get(UUID_1));
        assertEquals(testStorage[1], storage.get(UUID_2));
        assertEquals(testStorage[2], storage.get(UUID_3));
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertEquals(resume4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() {
        assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}

