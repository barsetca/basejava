package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public void update(Resume resume) {
        int index = notExist(resume.getUuid());
        updateResume(resume, index);
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            insertElement(resume, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = notExist(uuid);
        return getResume(index, uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = notExist(uuid);
        fillDeletedElement(index, uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public abstract Resume[] getAll();

    private int notExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
    @Override
    public abstract int size();

    @Override
    public abstract void clear();

    protected abstract void updateResume(Resume resume, int index);

    protected abstract Resume getResume(int index, String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract int getIndex(String uuid);

    protected abstract void fillDeletedElement(int index, String uuid);
}
