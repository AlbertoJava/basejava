package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (super.saveResume(r) & resumeCounter > 0) {
            Arrays.sort(storage, 0, resumeCounter);
            return;
        }
        return;
    }

    @Override
    public void delete(String uuid) {
        if (super.deleteResume(uuid) & resumeCounter > 0) {
            Arrays.sort(storage, 0, resumeCounter);
            return;
        }
        return;
    }

    @Override
    public void update(Resume resume) {
        if (super.updateResume(resume)) {
            Arrays.sort(storage, 0, resumeCounter);
            return;
        }
        return;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCounter, resume);
    }
}
