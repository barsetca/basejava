package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void insert(int index, Resume resume) {
        for (int i = size; i > -(1 + index); i--) {
            storage[i] = storage[i - 1];
        }
        storage[-(1 + index)] = resume;
    }

    protected void shift(int index) {
        for (int i = index; i < size; i++) {
            storage[i] = storage[i + 1];
        }
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}