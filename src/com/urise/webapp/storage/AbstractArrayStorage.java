package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected void clearAll() {
        Arrays.fill(storage, 0, size, null);
    }

    @Override
    public void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    public Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected abstract void fillDeletedElement(int index);

    @Override
    protected abstract void insertElement(Resume resume, int index);

    @Override
    protected abstract int getIndex(String uuid);
}
