package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateResume(Resume resume) {
        storage[getIndex(resume.getUuid())] = resume;
    }

    @Override
    public void saveResume(Resume resume) {

        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume);
            size++;
        }
    }

    @Override
    public void deleteResume(String uuid) {
        fillDeletedElement(uuid);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume getResume(String uuid) {
        return storage[getIndex(uuid)];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected abstract int getIndex(String uuid);

    protected abstract void fillDeletedElement(String uuid);

    protected abstract void insertElement(Resume resume);
}
