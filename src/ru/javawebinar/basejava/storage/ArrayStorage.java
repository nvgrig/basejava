package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    //сохранение нового resume в storage
    public void save(Resume resume) {
        if (size != storage.length) {
            if (getIndex(resume.getUuid()) == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", такое резюме есть в базе");
            }
        } else {
            System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", база резюме заполнена полностью");
        }

    }

    //удаление resume из storage
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: невозможно удалить \"" + uuid + "\", такого резюме нет в базе");
        }

    }

    //поиск позиции resume в storage
    protected int getIndex(String uuid) {
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
