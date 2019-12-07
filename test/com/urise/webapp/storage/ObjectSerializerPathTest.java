package com.urise.webapp.storage;


import com.urise.webapp.storage.strategy.ObjectSerializer;

public class ObjectSerializerPathTest extends AbstractStorageTest {


    public ObjectSerializerPathTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectSerializer()));
    }
}
