package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[MAX_SIZE];
    private int recordCounter = 0;
    private static final int MAX_SIZE = 10_0000;
    private static final String ERROR_MSG = "ERROR: record doesn't exists!";

    public void clear() {
        Arrays.fill(storage, null);
        recordCounter = 0;
    }

    public void save(Resume r) {
        if (getIndex(r.getUuid()) != -1 || recordCounter > MAX_SIZE) {
            System.out.println(ERROR_MSG);
            return;
        }
        storage[recordCounter++] = r;
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) >= 0) {
            return storage[getIndex(uuid)];
        }
        return null;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < recordCounter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }


    public void delete(String uuid) {
        if (getIndex(uuid) >= 0) {
            storage[getIndex(uuid)] = storage[recordCounter - 1];
            storage[recordCounter - 1] = null;
            recordCounter--;
        } else {
            System.out.println(ERROR_MSG);
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */


    public Resume[] getAll() {
        return Arrays.copyOf(storage, recordCounter);
    }

    public int size() {
        return recordCounter;
    }


    public boolean update(Resume resume) {
        int pos = getIndex(resume.getUuid());
        if (pos == -1) {
            System.out.println(ERROR_MSG);
            return false;
        }
        storage[pos].setUuid(resume.getUuid());
        return true;
    }
}
