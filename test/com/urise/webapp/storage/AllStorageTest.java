package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageUuidTest.class,
        SortedArrayStorageTest.class,
        MapStorageResumeTest.class,
        FileStorageTest.class,
        ObjectSerializerPathTest.class,
        XmlSerializerPathTest.class,
        JsonSerializerPathTest.class,
        DataSerializerPathTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}
