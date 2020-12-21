package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    protected static final Resume RESUME_1 = new Resume("uuid1", "Ricky Bobby");
    protected static final Resume RESUME_2 = new Resume("uuid2", "Sam Smith");
    protected static final Resume RESUME_3 = new Resume("uuid3", "Lance Dance");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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
        storage.update(RESUME_1);
        Assert.assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("Not Exist"));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResult = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName);
        expectedResult.sort(comparator);
        Assert.assertEquals(expectedResult, storage.getAllSorted());
    }

    @Test
    public void save() {
        Resume testResume = new Resume("Test Resume");
        storage.save(testResume);
        Assert.assertEquals(testResume, storage.get(testResume.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(RESUME_1.getUuid());
        storage.get(RESUME_1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("Not Exist");
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("Not Exist");
    }
}