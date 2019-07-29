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

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: resume with UUID = " + resume.getUuid() + "  already exist!. Resume can't be saved.");
            return;
        }
        if (resumeCounter >= MAX_SIZE) {
            System.out.println("ERROR: storage is overfilled. Resume with UUID = " + resume.getUuid() + " can't be saved.");
            return;
        }
        insertResume(index);
        storage[-index - 1] = resume;
        resumeCounter++;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("ERROR: resume with UUID = " + uuid + " not founded.");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: resume with UUID = " + uuid + " didn't found. Resume can't be deleted.");
            return;
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
            System.out.println("ERROR: resume with UUID = " + resume.getUuid() + " didn't found. Resume can't be updated.");
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
    protected abstract void insertResume(int index);

    protected abstract void deleteResume(int index);
}
