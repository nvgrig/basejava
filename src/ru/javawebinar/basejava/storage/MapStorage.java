package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

/**
 * List based storage for Resumes
 */
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
    protected void doUpdate(Resume resume, int index) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(int index, String uuid) {
        storage.remove(uuid);
    }

    protected int getIndex(String uuid) {
        return (storage.containsKey(uuid) ? 1 : -1);
    }
}
