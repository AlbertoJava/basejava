package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[MAX_SIZE];
    private int resumeCounter = 0;
    private static final int MAX_SIZE = 10_0000;
    private static final String ERROR_MSG = "ERROR: record doesn't exists!";

    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Resume with UUID " + resume.getUuid() + " already exist!");
            return;
        }
        if (resumeCounter >= MAX_SIZE) {
            System.out.println("Storage is full! Resume isn't saved!");
            return;
        }
        storage[resumeCounter++] = resume;
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
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[resumeCounter - 1];
            storage[resumeCounter - 1] = null;
            resumeCounter--;
        } else {
            System.out.println(ERROR_MSG);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCounter);
    }

    public int size() {
        return resumeCounter;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (!(index >= 0)) {
            System.out.println(ERROR_MSG);
            return;
        }
        storage[index] = resume;
        return;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
