package com.urise.webapp.storage;

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
    protected void insertResume(int index) {

        //storage[resumeCounter] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[resumeCounter - 1];
    }

}
