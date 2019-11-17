package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int searchKey = checkExist(resume.getUuid());
        updateResume(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        int searchKey = checkNotExist(resume.getUuid());
        saveResume(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = checkExist(uuid);
        return getResume(searchKey, uuid);
    }

    @Override
    public void delete(String uuid) {
        int searchKey = checkExist(uuid);
        deleteResume(searchKey, uuid);
    }

    private int checkExist(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        } else return searchKey;
    }

    private int checkNotExist(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey >= 0) {
            throw new ExistStorageException(uuid);
        } else return searchKey;
    }

    protected abstract void updateResume(int searchKey, Resume resume);

    protected abstract Resume getResume(int searchKey, String uuid);

    protected abstract void saveResume(int searchKey, Resume resume);

    protected abstract int getSearchKey(String uuid);

    protected abstract void deleteResume(int searchKey, String uuid);
}
