package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapStorageResume extends MapStorage {

    @Override
    protected boolean notExist(Object searchKey) {
        return searchKey == null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    public void deleteResume(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    public void saveResume(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void updateResume(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }
}
