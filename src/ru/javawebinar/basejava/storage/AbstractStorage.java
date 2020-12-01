package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void clear() {
        doClear();
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            doUpdate(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            doSave(resume, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return doGet(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(index);
        }
    }

    @Override
    public Resume[] getAll() {
        return doGetAll();
    }

    @Override
    public int size() {
        return doGetSize();
    }

    // операция по очистке
    protected abstract void doClear();

    // операция по обновлению
    protected abstract void doUpdate(Resume resume, int index);

    // операция по сохранению
    protected abstract void doSave(Resume resume, int index);

    // операция по получению
    protected abstract Resume doGet(int uuid);

    // операция по удалению
    protected abstract void doDelete(int uuid);

    // операция по получению всех элементов
    protected abstract Resume[] doGetAll();

    // операция по получению размера
    protected abstract int doGetSize();

    // поиск позиции resume в storage
    protected abstract int getIndex(String uuid);
}
