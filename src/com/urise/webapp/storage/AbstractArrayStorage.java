package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (resume == null) {
            System.out.println("Error update: param = null");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("update: Resume " + resume.getUuid() + " not exist");
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Error save: param = null");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            if (size == STORAGE_LIMIT) {
                System.out.println("save: Storage overflow");
            } else {
                insert(index, resume);
                size++;
            }
        } else {
            System.out.println("save: Resume " + resume.getUuid() + " already exist");
        }
    }

    protected abstract void insert(int index, Resume resume);

    public void delete(String uuid) {
        if (uuid == null) {
            System.out.println("Error delete: param = null");
            return;
        }
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("delete: Resume " + uuid + " not exist");
        } else {
            shift(index);
            storage[size - 1] = null;
            size--;
        }
    }

    protected abstract void shift(int index);

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        if (uuid == null) {
            System.out.println("Error get: param = null");
            return null;
        }
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("get: Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);
}
