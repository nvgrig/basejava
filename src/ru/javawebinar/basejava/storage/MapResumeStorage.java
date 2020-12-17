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
    public List<Resume> getAllSorted() {
        Resume[] resumes = storage.values().toArray(new Resume[0]);
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
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isResumeExist(Object searchKey) {
        return searchKey!=null;
    }
}
