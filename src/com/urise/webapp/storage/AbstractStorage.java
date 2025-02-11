package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;


public abstract class AbstractStorage implements Storage {
    protected final Comparator<Resume> RESUME_COMPARATOR_FULLNAME = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract List<Resume> doCopyAll();

    public List<Resume> getAllSorted() {
        List<Resume> resumes = doCopyAll();
        resumes.sort(RESUME_COMPARATOR_FULLNAME);
        return resumes;
    }


    public void save(Resume resume) {
        doSave(resume, getNotExistedSearchKey(resume.getUuid()));
    }

    public void delete(String uuid) {
        doDelete(getExistedSearchKey(uuid));
    }

    public void update(Resume resume) {
        doUpdate(resume, getExistedSearchKey(resume.getUuid()));
    }

    public Resume get(String uuid) {
        return doGet(getExistedSearchKey(uuid));
    }


    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
