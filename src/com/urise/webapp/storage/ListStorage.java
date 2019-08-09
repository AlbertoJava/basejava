package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        list.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        list.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return list.stream().sorted(RESUME_COMAPATOR).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return list.size();
    }
}
