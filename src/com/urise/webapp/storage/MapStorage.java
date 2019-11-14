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
    public void updateResume(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void saveResume(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume getResume(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void deleteResume(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        List<Resume> resumes = new ArrayList<>();
        storage.forEach((key, value) -> resumes.add(value));
        return resumes.toArray(new Resume[0]);
    }

    @Override
    protected int getIndex(String uuid) {
        if (storage.containsKey(uuid)) return 1;
        return -1;
    }
}
