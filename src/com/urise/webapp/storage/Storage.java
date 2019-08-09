package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

/**
 * Array based mapStorage for Resumes
 */
public interface Storage {
    Comparator<Resume> RESUME_COMAPATOR= (o1, o2) -> {
        if (o1.getFullName().equals(o2.getFullName()))
         return o1.getUuid().compareTo(o2.getUuid());
        return o1.getFullName().compareTo(o2.getFullName());
    };

    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();

    void update(Resume resume);
}
