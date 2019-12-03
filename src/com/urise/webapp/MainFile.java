package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {

        File file = new File("C:\\Users\\HP\\IdeaProjects\\basejava\\.gitignore");// получить путь ctrl + shift + c
        System.out.println(file.getAbsolutePath());
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        String filePath = ".\\.gitignore"; // 2 точки - родитель, 1 - текущий файл
        System.out.println(file.getParent());
        File file1 = new File(filePath);
        System.out.println(file.getAbsolutePath());
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getParent());

        File dir = new File("./src/com/urise/webapp"); // \\ - Win, 1 слэш / понимает - это из Unix
        System.out.println(dir.isDirectory());

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //java 7 auto closeable
        try (FileInputStream fis1 = new FileInputStream(filePath)) {
            System.out.println(fis1.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
// Lesson08 HW08 - 09
        String ourDirectoryPath = "C:\\Users\\HP\\IdeaProjects\\basejava\\src";
        File ourDirectory = new File(ourDirectoryPath);
        System.out.println(ourDirectory.getAbsolutePath());
        printAllDirectoryAndFiles(ourDirectory);

    }

    private static String space = "";

    public static void printAllDirectoryAndFiles(File dir) {

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(space + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(space + "Directory: " + file.getName());
                    space = space + "   ";
                    printAllDirectoryAndFiles(file);

                }
            }
        }
        if (space.length() > 2) {
            space = space.substring(3);
        }
    }
}
