package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isResumeExist(resume, searchKey)) {
            doUpdate(resume, searchKey);
        }
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isResumeNotExist(resume, searchKey)) {
            doSave(resume, searchKey);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume result = null;
        Object searchKey = getSearchKey(uuid);
        if (isResumeExist(new Resume(uuid), searchKey)) {
            result = doGet(searchKey);
        }
        return result;
    }


    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isResumeExist(new Resume(uuid), searchKey)) {
            doDelete(searchKey);
        }
    }

    // проверка на наличие резюме
    protected boolean isResumeExist(Resume resume, Object searchKey) {
        if (!(searchKey instanceof String)) {
            if ((int) searchKey < 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        }
        return true;
    }

    // проверка на отсутствие резюме
    protected boolean isResumeNotExist(Resume resume, Object searchKey) {
        if ((searchKey instanceof String) || ((int) searchKey >= 0)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return true;
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

}
