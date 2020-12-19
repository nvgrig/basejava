package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName);

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
    public Resume get(Resume resume) {
        Object searchKey = getExistStorageException(resume);
        return doGet(searchKey);
    }

    @Override
    public void delete(Resume resume) {
        Object searchKey = getExistStorageException(resume);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = getAll();
        resumeList.sort(RESUME_COMPARATOR);
        return resumeList;
    }

    // получаем существующий ключ
    protected Object getExistStorageException(Resume resume) {
        Object searchKey = getSearchKey(resume);
        if (!isResumeExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    // получаем несуществующий ключ
    protected Object getNotExistStorageException(Resume resume) {
        Object searchKey = getSearchKey(resume);
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

    // операция по получению элементов
    protected abstract List<Resume> getAll();

    // поиск позиции resume в storage
    protected abstract Object getSearchKey(Resume resume);

    // проверка наличия резюме
    protected abstract boolean isResumeExist(Object searchKey);
}
