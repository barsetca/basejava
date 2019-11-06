package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected void clearAll() {
        storage.clear();
    }

    @Override
    public void updateResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public void insertElement(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    public Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    public void fillDeletedElement(int index) {
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
