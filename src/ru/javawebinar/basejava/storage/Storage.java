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
    Resume get(Resume Resume);

    // delete resume from storage
    void delete(Resume resume);

    // get all resume from storage in sorted order
    List<Resume> getAllSorted();

    // get storage size
    int size();
}
