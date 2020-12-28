package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void saveInArray(int index, Resume resume) {

        int savePosition = -index - 1;

        if (savePosition != size) {
            // shifting array to the right
            System.arraycopy(storage, savePosition, storage, -index, size + index + 1);
        }
        storage[savePosition] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        // shifting array to the left
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
