package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCounter, resume);
    }

    @Override
    protected void insertResume(Resume resume) {
        int index = -Arrays.binarySearch(storage,0,resumeCounter,resume)-1;
        Resume[] second_part=Arrays.copyOfRange(storage,index,resumeCounter);
        System.arraycopy(second_part,0,storage,index+1,second_part.length);
        storage[index]=resume;
    }

    @Override
    protected void deleteResume(int index) {
        Resume[] second_part=Arrays.copyOfRange(storage,index+1,resumeCounter);
        System.arraycopy(second_part,0,storage,index,second_part.length);
        storage[resumeCounter-1]=null;
        resumeCounter--;
    }

}
