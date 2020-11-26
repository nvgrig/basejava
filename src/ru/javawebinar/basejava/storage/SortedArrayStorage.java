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
        // приводим результат поиска индекса из binary search в позицию для сохранения
        int savePosition = -index - 1;

        if (savePosition != size) {
            // сдвигаем массив вправо
            System.arraycopy(storage, savePosition, storage, -index, size + index + 1);
        }
        storage[savePosition] = resume;
    }

    protected void deleteInArray(int index) {
        // сдвигаем массив влево
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
