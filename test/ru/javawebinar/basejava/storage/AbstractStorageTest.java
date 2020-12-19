package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        storage.save(new Resume(UUID_1, "Ricky Bobby"));
        storage.save(new Resume(UUID_2, "Sam Smith"));
        storage.save(new Resume(UUID_3, "Lance Dance"));
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
        Resume expectedResume = new Resume(UUID_1, "Ricky Bobby");
        storage.update(expectedResume);
        Assert.assertEquals(expectedResume, storage.get(new Resume(UUID_1, "Ricky Bobby")));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("uuid4", "Micky"));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResult = new ArrayList<>();
        expectedResult.add(new Resume(UUID_1, "Ricky Bobby"));
        expectedResult.add(new Resume(UUID_2, "Sam Smith"));
        expectedResult.add(new Resume(UUID_3, "Lance Dance"));
        Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName);
        expectedResult.sort(comparator);
        Assert.assertEquals(expectedResult, storage.getAllSorted());
    }

    @Test
    public void save() {
        Resume testResume = new Resume("uuid4", "Micky");
        storage.save(testResume);
        Assert.assertEquals(testResume, storage.get(testResume));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1, "Ricky Bobby"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(new Resume(UUID_1, "Ricky Bobby"));
        storage.get(new Resume(UUID_1, "Ricky Bobby"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(new Resume("dummy", "Peter"));
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1, "Ricky Bobby"), storage.get(new Resume(UUID_1, "Ricky Bobby")));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(new Resume("dummy", "Peter"));
    }
}