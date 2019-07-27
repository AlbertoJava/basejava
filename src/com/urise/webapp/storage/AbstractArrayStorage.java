package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int resumeCounter = 0;
    private static final int MAX_SIZE = 10_0000;

    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    protected boolean saveResume(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("ERROR: resume with UUID = " + resume.getUuid() + "  already exist!. Resume can't be saved.");
            return false;
        }
        if (resumeCounter >= MAX_SIZE) {
            System.out.println("ERROR: storage is overfilled. Resume with UUID = " + resume.getUuid() + " can't be saved.");
            return false;
        }
        storage[resumeCounter++] = resume;
        return true;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("ERROR: resume with UUID = " + uuid + " not founded.");
        return null;
    }

    public boolean deleteResume(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[resumeCounter - 1];
            storage[resumeCounter - 1] = null;
            resumeCounter--;
            return true;
        } else {
            System.out.println("ERROR: resume with UUID = " + uuid + " didn't found. Resume can't be deleted.");
            return false;
        }
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

    public boolean updateResume(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            return true;
        }
        System.out.println("ERROR: resume with UUID = " + resume.getUuid() + " didn't found. Resume can't be updated.");
        return false;
    }

    protected abstract int getIndex(String uuid);
    public abstract void save(Resume resume);
    public abstract void delete(String uuid);
    public abstract void update(Resume resume);
}
