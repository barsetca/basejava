package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ReadWriteStorage {

    Resume readFileResume(InputStream in) throws IOException;

    void writeFileResume(Resume resume, OutputStream out) throws IOException;
}
