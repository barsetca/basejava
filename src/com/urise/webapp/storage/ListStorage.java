package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    public void updateResume(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    public void saveResume(Integer index, Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getResume(Integer index) {
        return storage.get(index);
    }

    @Override
    public void deleteResume(Integer index) {
        storage.remove((int) index);
    }

    @Override
    public List<Resume> getCopyList() {
        return new ArrayList<>(storage);
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
