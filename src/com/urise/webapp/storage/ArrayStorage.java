package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


/**
 * Array based mapStorage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    protected void insertResume(Resume resume, int index) {
        storage[resumeCounter] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[resumeCounter - 1];
    }

}
