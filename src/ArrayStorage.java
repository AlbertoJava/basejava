import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int lastRecord = 0;
    private static final String ERROR_MSG = "ERROR: record doesn't exists!";

    public void clear() {
        for (int i = 0; i < lastRecord; i++) {
            storage[i] = null;
        }
        lastRecord = 0;
    }

    public void save(Resume r) {
        if (get(r) != null) {
            System.out.println(ERROR_MSG);
            return;
        }
        storage[lastRecord++] = r;
    }

    public Resume get(String uuid) {
        if (getNumber(uuid) >= 0) {
            return storage[getNumber(uuid)];
        }
        return null;
    }

    private Resume get(Resume resume) {
        return get(resume.uuid);
    }

    private int getNumber(String uuid) {
        for (int i = 0; i < lastRecord; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }


    public void delete(String uuid) {
        if (getNumber(uuid) >= 0) {
            storage[getNumber(uuid)] = storage[lastRecord - 1];
            storage[lastRecord - 1] = null;
            lastRecord--;
        } else {
            System.out.println(ERROR_MSG);
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */


    public Resume[] getAll() {
        return Arrays.copyOf(storage, lastRecord);
    }

    public int size() {
        return lastRecord;
    }


    public boolean update(Resume resume) {
        int pos = getNumber(resume.uuid);
        if (pos == -1) {
            System.out.println(ERROR_MSG);
            return false;
        }
        get(storage[pos]).uuid = resume.uuid;
        return true;
    }
}
