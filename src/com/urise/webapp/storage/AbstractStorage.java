package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractStorage implements Storage {

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object existedSearchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract Stream<Resume> getStream();

    public List<Resume> getAllSorted(){
        return getStream().sorted().collect(Collectors.toList());
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

    /**
     * @return array, contains only Resumes in mapStorage (without null)
     */


}
