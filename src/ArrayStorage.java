import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int lastRecord=0;

    void clear() {
        lastRecord=0;
    }

    void save(Resume r) {
        storage[lastRecord++]=r;
    }

    Resume get(String uuid) {
        Resume result=null;
        for (int i=0;i<lastRecord;i++){
            if (storage[i].uuid.equals(uuid)){
                result=storage[i];
                break;
            }
        }
        return result;
    }

    void delete(String uuid) {
        for (int i=0;i<lastRecord;i++){
            if (storage[i].uuid.equals(uuid)){
                storage[i]=storage[lastRecord-1];
                storage[lastRecord-1]=null;
                lastRecord--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage,0,lastRecord);
    }

    int size() {
        return storage.length;
    }
}
