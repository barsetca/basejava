package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

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
    protected boolean notExist(String uuid) {
        return !storage.containsKey(uuid);
    }

    @Override
    public void updateResume(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    public void saveResume(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    public void deleteResume(Object searchKey) {
        storage.remove(searchKey.toString());
    }

    @Override
    public Resume[] getAll() {
        List<Resume> resumes = new ArrayList<>();
        storage.forEach((key, value) -> resumes.add(value));
        return resumes.toArray(new Resume[0]);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }
}
