package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
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

    @Override
    protected void getNotExistStorageException(Object searchKey, Resume resume) {
        if ((int) searchKey < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void getExistStorageException(Object searchKey, Resume resume) {
        if ((int) searchKey >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
    }
}
