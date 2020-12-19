package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume, RESUME_COMPARATOR);
    }

    @Override
    protected void saveInArray(int index, Resume resume) {
        // приводим результат поиска индекса из binary search в позицию для сохранения
        int savePosition = -index - 1;

        if (savePosition != size) {
            // сдвигаем массив вправо
            System.arraycopy(storage, savePosition, storage, -index, size + index + 1);
        }
        storage[savePosition] = resume;
    }

    @Override
    protected void deleteInArray(int index) {
        // сдвигаем массив влево
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
