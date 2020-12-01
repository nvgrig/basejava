package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void doSave(Resume resume, int index) {
        if (size == storage.length) {
            throw new StorageException("База резюме заполнена полностью", resume.getUuid());
        }
        saveInArray(index, resume);
        size++;
    }

    @Override
    protected Resume doGet(int index) {
        return storage[index];
    }

    @Override
    protected void doDelete(int index) {
        deleteInArray(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume[] doGetAll() {
        Resume[] resumes = new Resume[size];
        if (size > 0) System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    @Override
    protected int doGetSize() {
        return size;
    }

    // поиск позиции resume в storage
    protected abstract int getIndex(String uuid);

    // непосредственная операция сохранения в массив
    protected abstract void saveInArray(int index, Resume resume);

    // непосредственная операция удаления из массива
    protected abstract void deleteInArray(int index);
}
