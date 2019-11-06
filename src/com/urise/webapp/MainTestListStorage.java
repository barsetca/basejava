package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;

public class MainTestListStorage {
    private static final ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume resume3 = new Resume("uuid3");
        Resume resume1 = new Resume("uuid1");
        Resume resume2 = new Resume("uuid2");
        Resume resume4 = new Resume("dummy");

        LIST_STORAGE.save(resume3);
        LIST_STORAGE.save(resume1);
        LIST_STORAGE.save(resume2);

        System.out.println("Get resume1: " + LIST_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + LIST_STORAGE.size());

        System.out.println("Get resume4: " + LIST_STORAGE.get("dummy"));

        printAll();
        LIST_STORAGE.delete(resume4.getUuid());
        printAll();
        LIST_STORAGE.delete(resume1.getUuid());
        printAll();

        resume1 = new Resume("new");
        LIST_STORAGE.update(resume1);
        resume2 = new Resume("upgrade");
        LIST_STORAGE.update(resume2);
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
        for (Resume resume : LIST_STORAGE.getAll()) {
            System.out.println(resume);
        }
    }
}

