package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() {
        Resume[] testStorage = storage.getAll();
        Arrays.sort(testStorage);
        assertEquals(3, storage.getAll().length);
        HashMap<Integer, Resume> map = new HashMap<>();
        assertEquals(testStorage[0].getUuid(), storage.get(UUID_1).getUuid());
        assertEquals(testStorage[1].getUuid(), storage.get(UUID_2).getUuid());
        assertEquals(testStorage[2].getUuid(), storage.get(UUID_3).getUuid());
    }
}