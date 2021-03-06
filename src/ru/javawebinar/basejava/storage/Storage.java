package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {

    // storage clear
    void clear();

    // update resume in storage
    void update(Resume resume);

    // save resume in storage
    void save(Resume resume);

    // get resume from storage
    Resume get(String uuid);

    // delete resume from storage
    void delete(String uuid);

    // get all resume from storage in sorted order
    List<Resume> getAllSorted();

    // get storage size
    int size();
}
