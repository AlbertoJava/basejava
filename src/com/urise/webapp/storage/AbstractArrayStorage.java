package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int MAX_SIZE = 10_0000;

    public Resume[] getStorage() {
        return storage;
    }

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

    public List<Resume> getAllSorted() {
        return Arrays.stream(storage).limit(resumeCounter).collect(Collectors.toList());
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

    @Override
    protected Stream<Resume> getStream() {
        return Arrays.stream(storage,0,resumeCounter);
    }

    public int size() {
        return resumeCounter;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void insertResume(int index, Resume resume);

    protected abstract void deleteResume(int index);
}
