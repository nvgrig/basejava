package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("---------------------");
        System.out.println("recursive files print");
        try {
            printFiles(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // recursive files print
    public static void printFiles(File file) throws IOException {
        File[] list = file.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                printFiles(f);
                System.out.println("Dir:" + f.getName());
            } else {
                System.out.println("File:" + f.getName());
            }
        }
    }
}
