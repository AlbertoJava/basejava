package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    protected int getIndex(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume resume) {
        storage[resumeCounter] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[resumeCounter - 1];
        storage[resumeCounter - 1] = null;
        resumeCounter--;
    }

}
