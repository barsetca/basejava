package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void updateResume(Resume resume) {
        storage.set(getIndex(resume.getUuid()), resume);
    }

    @Override
    public void saveResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getResume(String uuid) {
        return storage.get(getIndex(uuid));
    }

    @Override
    public void deleteResume(String uuid) {
        storage.remove(get(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
