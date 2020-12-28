package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistSearchKey(resume);
        doUpdate(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotExistSearchKey(resume);
        doSave(resume, searchKey);
    }

    @Override
    public Resume get(Resume Resume) {
        SK searchKey = getExistSearchKey(Resume);
        return doGet(searchKey);
    }

    @Override
    public void delete(Resume resume) {
        SK searchKey = getExistSearchKey(resume);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = getAll();
        Collections.sort(resumeList);
        return resumeList;
    }

    // получаем существующий ключ
    private SK getExistSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume);
        if (!isResumeExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    // получаем несуществующий ключ
    private SK getNotExistSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume);
        if (isResumeExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    // операция по обновлению
    protected abstract void doUpdate(Resume resume, SK searchKey);

    // операция по сохранению
    protected abstract void doSave(Resume resume, SK searchKey);

    // операция по получению
    protected abstract Resume doGet(SK searchKey);

    // операция по удалению
    protected abstract void doDelete(SK searchKey);

    // операция по получению элементов
    protected abstract List<Resume> getAll();

    // поиск позиции resume в storage
    protected abstract SK getSearchKey(Resume resume);

    // проверка наличия резюме
    protected abstract boolean isResumeExist(SK searchKey);
}
