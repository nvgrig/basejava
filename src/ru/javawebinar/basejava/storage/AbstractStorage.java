package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

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
            return doGet(index, uuid);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            doDelete(index, uuid);
        }
    }

    // операция по обновлению
    protected abstract void doUpdate(Resume resume, int index);

    // операция по сохранению
    protected abstract void doSave(Resume resume, int index);

    // операция по получению
    protected abstract Resume doGet(int index, String uuid);

    // операция по удалению
    protected abstract void doDelete(int undex, String uuid);

    // поиск позиции resume в storage
    protected abstract int getIndex(String uuid);
}
