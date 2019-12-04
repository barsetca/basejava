package com.urise.webapp.strategy;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ReadWriteStrategy {

    Resume readResume(InputStream in) throws IOException;

    void writeResume(Resume resume, OutputStream out) throws IOException;
}
