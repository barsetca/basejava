package com.urise.webapp.storage;


import com.urise.webapp.storage.strategy.DataSerializer;

public class DataSerializerPathTest extends AbstractStorageTest {


    public DataSerializerPathTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataSerializer()));
    }
}
