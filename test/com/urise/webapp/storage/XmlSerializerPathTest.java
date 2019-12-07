package com.urise.webapp.storage;


import com.urise.webapp.storage.strategy.XmlSerializer;

public class XmlSerializerPathTest extends AbstractStorageTest {

    public XmlSerializerPathTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlSerializer()));
    }
}
