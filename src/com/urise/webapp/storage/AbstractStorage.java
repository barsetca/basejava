package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = checkExist(resume.getUuid());
        updateResume(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = checkNotExist(resume.getUuid());
        saveResume(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = checkExist(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = checkExist(uuid);
        deleteResume(searchKey);
    }

    private Object checkExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (notExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else return searchKey;
    }

    private Object checkNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!notExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumesReady = getList();
        Collections.sort(resumesReady);
        return resumesReady;
    }

    protected abstract boolean notExist(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void saveResume(Object searchKey, Resume resume);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void deleteResume(Object searchKey);

    protected abstract List<Resume> getList();
}
