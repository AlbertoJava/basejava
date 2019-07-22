import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int lastRecord = 0;

    void clear() {
        for (int i = 0; i < lastRecord; i++) {
            storage[i] = null;
        }
        lastRecord = 0;
    }

    void save(Resume r) {
        storage[lastRecord++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < lastRecord; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < lastRecord; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[lastRecord - 1];
                storage[lastRecord - 1] = null;
                lastRecord--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, lastRecord);
    }

    int size() {
        return lastRecord;
    }
}
