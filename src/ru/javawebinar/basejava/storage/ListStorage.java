package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] resumes = storage.toArray(new Resume[0]);
        Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName);
        Arrays.sort(resumes, comparator);
        return new ArrayList<>(Arrays.asList(resumes));
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(storage.get((int) searchKey));
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected boolean isResumeExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
