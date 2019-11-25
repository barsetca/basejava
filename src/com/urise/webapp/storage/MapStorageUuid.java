package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageUuid extends AbstractStorage<String> {

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
    public List<Resume> getCopyList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected boolean notExist(String uuid) {

        return !storage.containsKey(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public Resume getResume(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void deleteResume(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public void saveResume(String uuid, Resume resume) {
        storage.put(uuid, resume);
    }

    @Override
    public void updateResume(String uuid, Resume resume) {
        storage.put(uuid, resume);
    }
}
