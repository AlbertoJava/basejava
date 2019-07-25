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
        Arrays.fill(storage,0,recordCounter, null);
        recordCounter = 0;
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1){
            System.out.println("Record already exists!");
            return;
        }
        if (recordCounter >= MAX_SIZE) {
            System.out.println("Storage is full! Resume isn't saved!");
            return;
        }
        storage[recordCounter++] = resume;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println(ERROR_MSG);
        return null;
    }



    public void delete(String uuid) {
        int index =getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[recordCounter - 1];
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


    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println(ERROR_MSG);
            return;
        }
        storage[index].setUuid(resume.getUuid());
        return;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < recordCounter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
