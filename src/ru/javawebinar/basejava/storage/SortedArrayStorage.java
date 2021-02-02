package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;


public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void saveInArray(int index, Resume resume) {

        int savePosition = -index - 1;

        if (savePosition != size) {
            // shifting array to the right
            System.arraycopy(storage, savePosition, storage, -index, size + index + 1);
        }
        storage[savePosition] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        // shifting array to the left
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
