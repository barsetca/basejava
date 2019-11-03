package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;


public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private Resume resume1;
    private Resume resume2;
    private Resume resume3;
    private Resume resume4 = new Resume(UUID_4);

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    @Before
    public void setUp() {
        storage.clear();
        resume1 = new Resume(UUID_1);
        resume2 = new Resume(UUID_2);
        resume3 = new Resume(UUID_3);
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
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
        storage.update(resume3);
        Assert.assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume4);
        Assert.assertEquals(resume4, storage.get(UUID_4));
    }

    @Test
    public void getAll() {
        Resume[] testStorage = storage.getAll();
        Assert.assertEquals(3, storage.getAll().length);
        Assert.assertEquals(testStorage[0], storage.get(UUID_1));
        Assert.assertEquals(testStorage[1], storage.get(UUID_2));
        Assert.assertEquals(testStorage[2], storage.get(UUID_3));
    }

    @Test
    public void save() {
        storage.save(resume4);
        Assert.assertEquals(resume4, storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        Assert.assertEquals(STORAGE_LIMIT, storage.size());

        try {
            storage.save(new Resume());
            Assert.fail("Expected an StorageException to be thrown");
        } catch (StorageException e) {
            Assert.assertNotEquals("", e.getMessage());
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
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
        Assert.assertEquals(resume3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}
