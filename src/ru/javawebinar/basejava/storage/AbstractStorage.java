package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistStorageException(resume);
        doUpdate(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistStorageException(resume);
        doSave(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistStorageException(new Resume(uuid));
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistStorageException(new Resume(uuid));
        doDelete(searchKey);
    }

    // получаем существующий ключ
    protected Object getExistStorageException(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (!isResumeExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    // получаем несуществующий ключ
    protected Object getNotExistStorageException(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isResumeExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    // операция по обновлению
    protected abstract void doUpdate(Resume resume, Object searchKey);

    // операция по сохранению
    protected abstract void doSave(Resume resume, Object searchKey);

    // операция по получению
    protected abstract Resume doGet(Object searchKey);

    // операция по удалению
    protected abstract void doDelete(Object searchKey);

    // поиск позиции resume в storage
    protected abstract Object getSearchKey(String uuid);

    // проверка наличия резюме
    protected abstract boolean isResumeExist(Object searchKey);
}
