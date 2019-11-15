package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int mutableElement = checkExist(resume.getUuid());
        updateResume(mutableElement, resume);
    }

    @Override
    public void save(Resume resume) {
        int mutableElement = checkNotExist(resume.getUuid());
        saveResume(mutableElement, resume);
    }

    @Override
    public Resume get(String uuid) {
        int mutableElement = checkExist(uuid);
        return getResume(mutableElement, uuid);
    }

    @Override
    public void delete(String uuid) {
        int mutableElement = checkExist(uuid);
        deleteResume(mutableElement, uuid);
    }

    private int checkExist(String uuid) {
        int mutableElement = getIndex(uuid);
        if (mutableElement < 0) {
            throw new NotExistStorageException(uuid);
        } else return mutableElement;
    }

    private int checkNotExist(String uuid) {
        int mutableElement = getIndex(uuid);
        if (mutableElement >= 0) {
            throw new ExistStorageException(uuid);
        } else return mutableElement;
    }

    protected abstract void updateResume(int mutableElement, Resume resume);

    protected abstract Resume getResume(int mutableElement, String uuid);

    protected abstract void saveResume(int mutableElement, Resume resume);

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int mutableElement, String uuid);
}
