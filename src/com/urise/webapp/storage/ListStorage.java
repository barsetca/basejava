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
    protected boolean notExist(Object index) {
        return (int) index < 0;
    }

    @Override
    public void updateResume(Object index, Resume resume) {
        storage.set((int) index, resume);
    }

    @Override
    public void saveResume(Object index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getResume(Object index) {
        return storage.get((int) index);
    }

    @Override
    public void deleteResume(Object index) {
        storage.remove((int) index);
    }

    @Override
    public List<Resume> getList(List<Resume> resumes) {
        return storage;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
