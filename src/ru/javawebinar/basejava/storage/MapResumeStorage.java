package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        Resume foundedResume = (Resume) searchKey;
        storage.replace(foundedResume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume foundedResume = (Resume) searchKey;
        storage.remove(foundedResume.getUuid());
    }

    @Override
    protected Object getSearchKey(Resume resume) {
        return resume;
    }

    @Override
    protected boolean isResumeExist(Object searchKey) {
        return storage.containsValue(searchKey);
    }
}
