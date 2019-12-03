package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements ReadWriteStorage {

//    private File directory;
//
//    public ObjectStreamStorage(File directory) {
//        this.directory = directory;
//    }

    @Override
    public Resume readFileResume(InputStream in) throws IOException {
        try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
            return (Resume) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
    public void writeFileResume(Resume resume, OutputStream out) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(out)) {
            objectOut.writeObject(resume);
        }
    }
}
