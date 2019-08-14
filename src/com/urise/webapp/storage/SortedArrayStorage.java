package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid, "defaultFullName");
        return Arrays.binarySearch(storage, 0, resumeCounter, resume, RESUME_COMPARATOR_UUID);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int indexResume = -index - 1;
        System.arraycopy(storage, indexResume, storage, indexResume + 1, resumeCounter - indexResume);
        storage[indexResume] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCounter - index - 1);
    }

}
