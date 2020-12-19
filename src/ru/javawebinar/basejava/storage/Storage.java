package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {

    // полная очистка storage
    void clear();

    // обновление resume, которое имеется в storage
    void update(Resume resume);

    // сохранение нового resume в storage
    void save(Resume resume);

    // получение resume из storage
    Resume get(Resume resume);

    // удаление resume из storage
    void delete(Resume resume);

    // получение всех resume из storage
    List<Resume> getAllSorted();

    // получение количества resume в storage
    int size();
}
