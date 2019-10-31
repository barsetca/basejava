package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;

public class MainTestSortedArrayStorage {
    private static final SortedArrayStorage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume resume3 = new Resume("uuid3");
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume4 = new Resume("dummy");

        SORTED_ARRAY_STORAGE.save(resume3);
        SORTED_ARRAY_STORAGE.save(resume1);
        SORTED_ARRAY_STORAGE.save(resume2);

        System.out.println("Get resume1: " + SORTED_ARRAY_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        System.out.println("Get resume4: " + SORTED_ARRAY_STORAGE.get("dummy"));

        printAll();
        SORTED_ARRAY_STORAGE.delete(resume4.getUuid());
        printAll();
        SORTED_ARRAY_STORAGE.delete(resume1.getUuid());
        printAll();

        resume1 = new Resume("new");
        SORTED_ARRAY_STORAGE.update(resume1);
        resume2 = new Resume("upgrade");
        SORTED_ARRAY_STORAGE.update(resume2);
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
        for (Resume resume : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(resume);
        }
    }
}

