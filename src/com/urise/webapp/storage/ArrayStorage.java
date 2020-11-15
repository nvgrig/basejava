package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    //проверка storage на переполнение
    public boolean isOverflow() {
        boolean result = false;
        if (size == 10_000) {
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
        if (resumeFind(r.getUuid()) >= 0) {
            storage[resumeFind(r.getUuid())] = r;
        } else {
            System.out.println("ERROR: невозможно обновить \"" + r.getUuid() + "\", такого резюме нет в базе");
        }
    }

    //сохранение нового resume в storage
    public void save(Resume r) {
        if (!isOverflow()) {
            if (resumeFind(r.getUuid()) == -1) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("ERROR: невозможно сохранить \"" + r.getUuid() + "\", такое резюме есть в базе");
            }
        } else {
            System.out.println("ERROR: невозможно сохранить \"" + r.getUuid() + "\", база резюме заполнена полностью");
        }
    }

    //получение resume из storage
    public Resume get(String uuid) {
        if (resumeFind(uuid) >= 0) {
            return storage[resumeFind(uuid)];
        } else {
            System.out.println("ERROR: невозможно получить \"" + uuid + "\", такого резюме нет в базе");
        }
        return null;
    }

    //удаление resume из storage
    public void delete(String uuid) {
        if (resumeFind(uuid) >= 0) {
            storage[resumeFind(uuid)] = storage[size - 1];
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
}
