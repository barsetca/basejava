package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final int STORAGE_LIMIT = 10_000;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    @Before
    public void setUp() {
        storage.clear();
        Resume resume1 = new Resume(UUID_1);
        Resume resume2 = new Resume(UUID_2);
        Resume resume3 = new Resume(UUID_3);
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @After
    public void clearStorage() {
        storage.clear();
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume1 = new Resume(UUID_1);
        storage.update(resume1);
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume4 = new Resume(UUID_4);
        storage.update(resume4);
        Assert.assertEquals(resume4, storage.get(UUID_4));
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void save() {
        Resume resume4 = new Resume(UUID_4);
        storage.save(resume4);
        Assert.assertEquals(resume4, storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void saveFillAll() {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        Assert.assertEquals(STORAGE_LIMIT, storage.size());
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT + 1; i++) {
            storage.save(new Resume());
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume resume3 = new Resume(UUID_3);
        storage.save(resume3);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() {
        Resume resume3 = new Resume(UUID_3);
        Assert.assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}