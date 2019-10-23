package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        if (size > 0) {
            Arrays.fill(storage, 0, size - 1, null);
            size = 0;
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else System.out.println("Error update(Resume r): the Resume with uuid <" + r.toString()
                + "> isn't in the storage");
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Error save (Resume r): the storage is full. Resume <" + r.toString()
                    + "> is impossible to save.");
            return;
        }
        int index = getIndex(r.getUuid());
        if (index < 0) {
            storage[size] = r;
            size++;
        } else System.out.println("Error save(Resume r): resume <" + r.toString()
                + "> is already in the storage");
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error get(uuid): the Resume with uuid = <" + uuid
                    + "> isn't in the storage");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error delete(uuid) : the Resume with uuid = <" + uuid
                    + "> isn't in the storage");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return size;
    }

    /**
     * @param uuid - the String field of the Resume
     * @return int >= 0 - index Resume in the storage, if the Resume with this uuid is in the storage
     * int = -1 - if the Resume with this uuid is not in the storage
     */
    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
