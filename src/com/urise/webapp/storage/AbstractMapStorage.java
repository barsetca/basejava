package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getList(List<Resume> resumes) {
        storage.forEach((key, value) -> resumes.add(value));
        return resumes;
    }
}
