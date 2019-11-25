package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

public class MainTestSortedArrayStorage {

    private static final SortedArrayStorage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {

        Resume resume1 = new Resume("uuid1", "a");
        Resume resume2 = new Resume("uuid2", "b");
        Resume resume3 = new Resume("uuid3", "c");
        Resume resume4 = new Resume("uuid4", "d");

        SORTED_ARRAY_STORAGE.save(resume2);
        SORTED_ARRAY_STORAGE.save(resume1);
        SORTED_ARRAY_STORAGE.save(resume3);

        printAll();

        System.out.println("Get uuid1: " + resume1.getUuid());
        System.out.println("Get resume1: " + SORTED_ARRAY_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        printAll();
        SORTED_ARRAY_STORAGE.delete(resume1.getUuid());
        printAll();

        SORTED_ARRAY_STORAGE.save(resume1);
        SORTED_ARRAY_STORAGE.save(resume4);
        printAll();

        SORTED_ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume resume : SORTED_ARRAY_STORAGE.getAllSorted()) {
            System.out.println(resume);
        }
    }
}

