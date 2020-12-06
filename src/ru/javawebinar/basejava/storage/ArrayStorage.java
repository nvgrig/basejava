package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage{

    @Override
    protected Object getSearchKey(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected void saveInArray(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteInArray(int index) {
        storage[index] = storage[size - 1];
    }
}
