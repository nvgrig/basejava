package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
            //System.out.println("ERROR: невозможно обновить \"" + resume.getUuid() + "\", такого резюме нет в базе");
        }
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            throw new StorageException("База резюме заполнена полностью", resume.getUuid());
            //System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", база резюме заполнена полностью");
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
            //System.out.println("ERROR: невозможно сохранить \"" + resume.getUuid() + "\", такое резюме есть в базе");
        } else {
            saveInArray(index, resume);
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
        //System.out.println("ERROR: невозможно получить \"" + uuid + "\", такого резюме нет в базе");
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: невозможно удалить \"" + uuid + "\", такого резюме нет в базе");
        } else {
            deleteInArray(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        if (size > 0) System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }

    // поиск позиции resume в storage
    protected abstract int getIndex(String uuid);

    // непосредственная операция сохранения в массив
    protected abstract void saveInArray(int index, Resume resume);

    // непосредственная операция удаления из массива
    protected abstract void deleteInArray(int index);
}
