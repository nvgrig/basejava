package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return (storage.containsKey(uuid) ? uuid : "-1");
    }

    @Override
    protected void getNotExistStorageException(Object searchKey, Resume resume) {
        if (searchKey.equals("-1") ) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    protected void getExistStorageException(Object searchKey, Resume resume) {
        if (searchKey.equals(resume.getUuid())) {
            throw new ExistStorageException(resume.getUuid());
        }
    }
}
