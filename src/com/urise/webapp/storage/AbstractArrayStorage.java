package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int MAX_SIZE = 10_000;
    protected final Comparator<Resume> RESUME_COMPARATOR_UUID = Comparator.comparing(Resume::getUuid);
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int resumeCounter = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    public void doDelete(Object index) {
        deleteResume((Integer) index);
        storage[resumeCounter - 1] = null;
        resumeCounter--;
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
        insertResume(resume, (Integer) index);
        resumeCounter++;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, resumeCounter));
    }

    @Override
    public int size() {
        return resumeCounter;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}
