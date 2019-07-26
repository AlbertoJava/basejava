package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void clear();

    boolean save(Resume r);

    Resume get(String uuid);

    boolean delete(String uuid);

    Resume[] getAll();

    int size();

    boolean update(Resume resume);
}
