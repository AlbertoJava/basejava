package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage{
    Map<String,Resume> mapStorage = new HashMap();

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        mapStorage.put((String)searchKey,resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.get(searchKey)!=null;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        mapStorage.put((String)searchKey,resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return  mapStorage.values().toArray(new Resume[mapStorage.size()]);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
