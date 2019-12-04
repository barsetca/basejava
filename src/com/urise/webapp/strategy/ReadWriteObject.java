package com.urise.webapp.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ReadWriteObject implements ReadWriteStrategy {

    @Override
    public Resume readResume(InputStream in) throws IOException {
        try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
            return (Resume) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
    public void writeResume(Resume resume, OutputStream out) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(out)) {
            objectOut.writeObject(resume);
        }
    }
}
