/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) storage[i] = null;
            if (storage[i] == null) break;
        }
    }

    void save(Resume r) {
        size++;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) break;
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        size--;
        int deletedResume = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                deletedResume = i;
                break;
            }
        }
        for (int i = deletedResume + 1; i < storage.length - 1; i++) {
            storage[i - 1] = storage[i];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int resumesSize = 0;
        if (storage[0] == null) return new Resume[0];
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                resumesSize = i;
                break;
            }
        }
        Resume[] resumes = new Resume[resumesSize];
        for (int i = 0; i < resumes.length; i++) resumes[i] = storage[i];
        return resumes;
    }

    int size() {
        return size;
    }
}
