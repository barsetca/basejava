package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Error update: Resume with uuid <" + resume.toString()
                    + "> isn't in the storage");
        } else storage[index] = resume;
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Error save: storage is full. Resume with uuid <" + resume.toString()
                    + "> is impossible to save.");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            storage[size] = resume;
            size++;
        } else System.out.println("Error save: Resume with uuid <" + resume.toString()
                + "> is already in the storage");
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error get: Resume with uuid = <" + uuid
                    + "> isn't in the storage");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Error delete: the Resume with uuid = <" + uuid
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
        return Arrays.copyOfRange(storage, 0, size);
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
