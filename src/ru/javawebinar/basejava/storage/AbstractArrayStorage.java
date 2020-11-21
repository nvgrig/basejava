package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    //полная очистка storage
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    //получение всех resume из storage
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        if (size > 0) System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    //получение количества resume в storage
    public int size() {
        return size;
    }

    //получение resume из storage
    public Resume get(String uuid) {
        int index = findResume(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("ERROR: невозможно получить \"" + uuid + "\", такого резюме нет в базе");
        return null;
    }

    //поиск позиции resume в storage
    protected abstract int findResume(String uuid);
}
