package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        checkExist(index, resume.getUuid());
        updateResume(index, resume);
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        checkNotExist(index, resume.getUuid());
        saveResume(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        checkExist(index, uuid);
        return getResume(index, uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        checkExist(index, uuid);
        deleteResume(index, uuid);
    }

    private void checkExist(int index, String uuid) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void checkNotExist(int index, String uuid) {
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract void updateResume(int index, Resume resume);

    protected abstract Resume getResume(int index, String uuid);

    protected abstract void saveResume(int index, Resume resume);

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index, String uuid);
}
