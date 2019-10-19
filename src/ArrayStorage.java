/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int realSize = 0;

    void clear() {
        for (int i = 0; i < realSize; i++) {
            storage[i] = null;
        }
        realSize = 0;
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                realSize++;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume resume1 = null;
        for (int i = 0; i < realSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume1 = storage[i];
            }
            break;
        }
        return resume1;
    }

    void delete(String uuid) {
        for (int i = 0; i < realSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[realSize - 1];
                storage[realSize - 1] = null;
                realSize--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[realSize];
        for (int i = 0; i < realSize; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        return realSize;
    }
}
