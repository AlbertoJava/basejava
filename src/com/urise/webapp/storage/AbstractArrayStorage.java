package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int MAX_SIZE = 10_0000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int resumeCounter = 0;

    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    @Override
    protected Resume doGet(Object existedSearchKey) {
        return storage[(Integer) existedSearchKey];
    }

    @Override
    public void doDelete(Object index) {
        deleteResume((Integer) index);
        storage[resumeCounter - 1] = null;
        resumeCounter--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCounter);
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (resumeCounter >= MAX_SIZE) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertResume((Integer) index, resume);
        resumeCounter++;
    }

    public int size() {
        return resumeCounter;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    /*
        method returns:
        1. Positive Index in storage, including zero,  for existing element
        2. Negative index for absent element. Result equals |index| of element, wich is next to absent element.
         */
    protected abstract Integer getSearchKey(String uuid);

    /*
     * parametr index equals index of element in storage, wich is next to inserting element
     * */
    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
