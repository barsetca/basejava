package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume extends AbstractStorage<Resume> {

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
    protected boolean notExist(Resume resumeSearchKey) {
        return resumeSearchKey == null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public Resume getResume(Resume resumeSearchKey) {
        return  resumeSearchKey;
    }

    @Override
    public void deleteResume(Resume resumeSearchKey) {
        storage.remove(resumeSearchKey.getUuid());
    }

    @Override
    public void saveResume(Resume resumeSearchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void updateResume(Resume resumeSearchKey, Resume resume) {
        storage.put(resumeSearchKey.getUuid(), resume);
    }
}
