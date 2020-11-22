package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    //сохранение нового resume в storage
    @Override
    public void save(Resume resume) {
        if (size != storage.length) {
            int index = getIndex(resume.getUuid());
            if (index < 0) {
                if (-index - 1 == size) {
                    storage[-index - 1] = resume;
                    size++;
                } else {
                    //сдвигаем массив вправо
                    System.arraycopy(storage, -index - 1, storage, -index, size + index + 1);
                    storage[-index - 1] = resume;
                    size++;
                }
            } else {
                System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", такое резюме есть в базе");
            }
        } else {
            System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", база резюме заполнена полностью");
        }
    }

    //удаление resume из storage
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            if (size - 1 - index >= 0) {
                //сдвигаем массив влево
                System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            }
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: невозможно удалить \"" + uuid + "\", такого резюме нет в базе");
        }
    }

    //поиск позиции resume в storage
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
