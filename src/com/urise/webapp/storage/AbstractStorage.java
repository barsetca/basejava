package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public void update(Resume resume) {
        if (isExist(resume.getUuid())) {
            throw new NotExistStorageException(resume.getUuid());
        } else updateResume(resume);
    }

    @Override
    public void save(Resume resume) {
        if (isExist(resume.getUuid())) {
            saveResume(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        if (isExist(uuid)) throw new NotExistStorageException(uuid);
        return getResume(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        } else deleteResume(uuid);
    }

    @Override
    public abstract Resume[] getAll();

    private boolean isExist(String uuid) {
        int index = getIndex(uuid);
        return index < 0;
    }

    @Override
    public abstract int size();

    @Override
    public abstract void clear();

    protected abstract void updateResume(Resume resume);

    protected abstract Resume getResume(String uuid);

    protected abstract void saveResume(Resume resume);

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(String uuid);
}
