/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) storage[i] = null;
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int deletedResume = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                deletedResume = i;
                break;
            }
        }
        for (int i = deletedResume + 1; i < size - 1; i++) {
            storage[i - 1] = storage[i];
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (size == 0) return new Resume[0];
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) resumes[i] = storage[i];
        return resumes;
    }

    int size() {
        return size;
    }
}
