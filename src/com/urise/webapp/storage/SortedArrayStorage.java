package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid,"defaultFullName");
       // if (resumeCounter==0) return -1;
       // String[] uuids = Arrays.stream(storage).limit(resumeCounter).map(r->r.getUuid()).toArray(String[]::new);
       // Arrays.stream(storage).filter(r->r.getUuid().equals(uuid)).count();
        //return Arrays.binarySearch(storage, 0, resumeCounter, resume);
        /* Arrays.stream(storage)
                .sorted(Comparator.comparing(Resume::getUuid))
                .toArray();*/
        //return Arrays.binarySearch(uuids,uuid);
        return  Arrays.binarySearch(storage,0,resumeCounter,resume,RESUME_COMAPATOR);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int indexResume = -index - 1;
        System.arraycopy(storage, indexResume, storage, indexResume + 1, resumeCounter - indexResume);
        storage[indexResume] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCounter - index - 1);
    }

}
