package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapStorageUuid extends AbstractMapStorage {

    @Override
    protected boolean notExist(Object uuid) {

        return !storage.containsKey(uuid.toString());
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
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
    public void saveResume(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    public void updateResume(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }
}
