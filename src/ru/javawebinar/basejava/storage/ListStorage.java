package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void doClear() {
        storage.clear();
    }

    @Override
    public void doUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public void doSave(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    public Resume doGet(int index) {
        return storage.get(index);
    }

    @Override
    public void doDelete(int index) {
        storage.remove(storage.get(index));
        storage.trimToSize();
    }

    @Override
    public Resume[] doGetAll() {
        return (Resume[]) storage.toArray();
    }

    @Override
    public int doGetSize() {
        return storage.size();
    }

    protected int getIndex(String uuid) {return 0;};
}
