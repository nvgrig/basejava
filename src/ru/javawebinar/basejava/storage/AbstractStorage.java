package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = isResumeExist(resume);
        doUpdate(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = isResumeNotExist(resume);
        doSave(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = isResumeExist(new Resume(uuid));
        return doGet(searchKey);
    }


    @Override
    public void delete(String uuid) {
        Object searchKey = isResumeExist(new Resume(uuid));
        doDelete(searchKey);
    }

    // проверка на наличие резюме
    protected Object isResumeExist(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        getNotExistStorageException(searchKey, resume);
        return searchKey;
    }

    // проверка на отсутствие резюме
    protected Object isResumeNotExist(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        getExistStorageException(searchKey, resume);
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

    // проверка исключение notExist
    protected abstract void getNotExistStorageException(Object searchKey, Resume resume);

    // проверка исключение Exist
    protected abstract void getExistStorageException(Object searchKey, Resume resume);

}
