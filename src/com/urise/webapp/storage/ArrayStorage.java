package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

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
        return -(resumeCounter+1);
    }

    @Override
    protected void insertResume(int index,Resume resume) {
        storage[-index - 1] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[resumeCounter - 1];
    }

}
