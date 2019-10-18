import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume resume1 = null;
        for (int i = 0; i < this.size(); i++) {
            if (storage[i].uuid.equals(uuid)) resume1 = storage[i];
            break;
        }
        return resume1;
    }

    void delete(String uuid) {
        for (int i = 0; i < this.size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = this.storage[this.size() - 1];
                storage[this.size() - 1] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[this.size()];
        for (int i = 0; i < this.size(); i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                size = i;
                break;
            }
        }
        return size;
    }
}
