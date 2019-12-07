package com.urise.webapp.storage;


import com.urise.webapp.storage.strategy.JsonSerializer;

public class JsonSerializerPathTest extends AbstractStorageTest {

    public JsonSerializerPathTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonSerializer()));
    }
}
