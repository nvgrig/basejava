package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    //полная очистка storage
    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    //обновление resume, которое имеется в storage
    public void update(Resume resume) {
        int index = findResume(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("ERROR: невозможно обновить \"" + resume.getUuid() + "\", такого резюме нет в базе");
        }
    }

    //сохранение нового resume в storage
    public void save(Resume resume) {
        boolean isOverflowed = false;
        if (size == 10_000) {
            isOverflowed = true;
        }
        if (!isOverflowed) {
            if (findResume(resume.getUuid()) == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", такое резюме есть в базе");
            }
        } else {
            System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", база резюме заполнена полностью");
        }
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

    //удаление resume из storage
    public void delete(String uuid) {
        int index = findResume(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: невозможно удалить \"" + uuid + "\", такого резюме нет в базе");
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    //получение всех resume из storage
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        if (size >= 0) System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    //получение количества resume в storage
    public int size() {
        return size;
    }

    //поиск позиции resume в storage
    private int findResume(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
