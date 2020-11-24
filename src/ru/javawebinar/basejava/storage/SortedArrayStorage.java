package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void saveInArray(int index, Resume resume) {
        if (-index - 1 == size) {
            storage[-index - 1] = resume;
        } else {
            // сдвигаем массив вправо
            System.arraycopy(storage, -index - 1, storage, -index, size + index + 1);
            storage[-index - 1] = resume;
        }
    }

    protected void deleteInArray(int index) {
        // сдвигаем массив влево
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
