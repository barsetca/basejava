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
    public void updateResume(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    public void saveResume(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getResume(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    public void deleteResume(int index, String uuid) {
        storage.remove(index);
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
