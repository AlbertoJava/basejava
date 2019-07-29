package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCounter, resume);
    }

    @Override
    protected void insertResume(Resume resume) {
        int index = -getIndex(resume.getUuid()) - 1;
        System.arraycopy(storage, index, storage, index + 1, resumeCounter);
        storage[index] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCounter);

    }

}
