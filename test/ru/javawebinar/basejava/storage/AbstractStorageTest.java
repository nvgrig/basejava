package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        Resume resume1 = new Resume(UUID_1);
        resume1.setFullName("Ricky Bobby");
        Resume resume2 = new Resume(UUID_2);
        resume2.setFullName("Sam Smith");
        Resume resume3 = new Resume(UUID_3);
        resume3.setFullName("Lance Dance");
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume expectedResume = new Resume(UUID_1);
        storage.update(expectedResume);
        Assert.assertEquals(expectedResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume());
    }

    @Test
    public void getAllSorted() {
        Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName);
        Resume resume1 = new Resume(UUID_1);
        resume1.setFullName("Ricky Bobby");
        Resume resume2 = new Resume(UUID_2);
        resume2.setFullName("Sam Smith");
        Resume resume3 = new Resume(UUID_3);
        resume3.setFullName("Lance Dance");
        Resume[] expectedResult = new Resume[]{resume1, resume2, resume3};
        Arrays.sort(expectedResult, comparator);
        Resume[] result = storage.getAllSorted().toArray(new Resume[0]);
        Assert.assertEquals(expectedResult[0], result[0]);
        Assert.assertEquals(expectedResult[1], result[1]);
        Assert.assertEquals(expectedResult[2], result[2]);
    }

    @Test
    public void save() {
        Resume testResume = new Resume();
        storage.save(testResume);
        Assert.assertEquals(testResume, storage.get(testResume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}