package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public boolean save(Resume r) {
        if (super.save(r) & resumeCounter > 0) {
            Arrays.sort(storage, 0, resumeCounter);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String uuid) {
        if (super.delete(uuid) & resumeCounter > 0) {
            Arrays.sort(storage, 0, resumeCounter);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Resume resume) {
        if (super.update(resume)) {
            Arrays.sort(storage, 0, resumeCounter);
            return true;
        }
        return false;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCounter, resume);
    }
}
