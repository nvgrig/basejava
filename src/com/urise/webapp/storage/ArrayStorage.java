package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    //проверка на наличие resume в storage
    public boolean isPresent(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        boolean result = false;
        if (resumeFind(uuid) >= 0) {
            result = true;
        }
        return result;
    }

    //проверка storage на переполнение
    public boolean isOverflow() {
        boolean result = false;
        if (size == 10000) {
            result = true;
        }
        return result;
    }

    //поиск позиции resume в storage
    public int resumeFind(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(r.getUuid())) {
                result = i;
                break;
            }
        }
        return result;
    }

    //полная очистка storage
    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    //обновление resume, которое имеется в storage
    public void update(Resume r) {
        if (isPresent(r.getUuid())) {
              storage[resumeFind(r.getUuid())] = r;
        }
    }

    //сохранение нового resume в storage
    public void save(Resume r) {
        if (!isOverflow()) {
            if (!isPresent(r.getUuid())) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("ERROR: невозможно сохранить, такое резюме есть в базе");
            }
        } else{
            System.out.println("ERROR: невозможно сохранить, база резюме заполнена полностью");
        }
    }

    //получение resume из storage
    public Resume get(String uuid) {
        if (isPresent(uuid)) {
            return storage[resumeFind(uuid)];
        } else {
            System.out.println("ERROR: невозможно получить, такого резюме нет в базе");
        }
        return null;
    }

    //удаление resume из storage
    public void delete(String uuid) {
        if (isPresent(uuid)) {
            storage[resumeFind(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: невозможно удалить, такого резюме нет в базе");
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    //получение всех resume из storage
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    //получение количества resume в storage
    public int size() {
        return size;
    }
}
