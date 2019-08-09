package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStorage2Version extends AbstractStorage {
    private Map<String, Resume> mapStorage = new HashMap();

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        mapStorage.put(((Resume)searchKey).getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return mapStorage.containsKey(((Resume)searchKey).getUuid());
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        mapStorage.put(((Resume)searchKey).getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return mapStorage.get(((Resume)searchKey).getUuid());
    }

    @Override
    protected void doDelete(Object searchKey) {
        mapStorage.remove(((Resume)searchKey).getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return mapStorage.values().stream().sorted(RESUME_COMAPATOR).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
