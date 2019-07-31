package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    private static final int MAX_SIZE = 10_0000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int resumeCounter = 0;

    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        if (resumeCounter >= MAX_SIZE) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertResume(index, resume);
        resumeCounter++;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(index);
        storage[resumeCounter - 1] = null;
        resumeCounter--;
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
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage[index] = resume;
    }
    /*
    method returns:
    1. Positive Index in storage, including zero,  for existing element
    2. Negative index for absent element. Result equals |index| of element, wich is next to absent element.
     */

    protected abstract int getIndex(String uuid);

    /*
     * parametr index equals index of element in storage, wich is next to inserting element
     * */
    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
