package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;

public class MainTestListStorage {
    private static final ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume resume3 = new Resume("uuid3", "c");
        Resume resume1 = new Resume("uuid1", "d");
        Resume resume2 = new Resume("uuid2", "a");
        Resume resume4 = new Resume("dummy", "b");

        LIST_STORAGE.save(resume3);
        LIST_STORAGE.save(resume1);
        LIST_STORAGE.save(resume2);

        System.out.println("Get resume1: " + LIST_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + LIST_STORAGE.size());

        printAll();
        printAll();
        LIST_STORAGE.delete(resume1.getUuid());
        printAll();

        LIST_STORAGE.save(resume1);
        LIST_STORAGE.save(resume4);
        printAll();

        LIST_STORAGE.clear();
        printAll();

        System.out.println("Size: " + LIST_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");
        for (Resume resume : LIST_STORAGE.getAllSorted()) {
            System.out.println(resume);
        }
    }
}

