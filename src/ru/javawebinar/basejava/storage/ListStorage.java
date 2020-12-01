package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void doSave(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(int index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(int index) {
        storage.remove(storage.get(index));
        storage.trimToSize();
    }

    @Override
    protected Resume[] doGetAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected int doGetSize() {
        return storage.size();
    }

    protected int getIndex(String uuid) {return storage.indexOf(new Resume(uuid));}
}
