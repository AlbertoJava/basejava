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
    public void save(Resume resume) {
        super.saveResume(resume);
    }

    @Override
    public void delete(String uuid) {
         super.deleteResume(uuid);
    }

    @Override
    public void update(Resume resume) {
          super.updateResume(resume);
    }
}
