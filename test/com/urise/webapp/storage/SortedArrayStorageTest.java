package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    public void getIndex() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String uuid = storage.getAll()[0].getUuid();
        Method getInd = storage.getClass().getDeclaredMethod("getIndex", String.class);
        Assert.assertEquals(0, (int) getInd.invoke(storage, uuid));
    }

    @Test
    public void insertResume() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume = new Resume("UUID");
        Method insertResume = storage.getClass().getDeclaredMethod("insertResume", int.class, Resume.class);
        insertResume.invoke(storage,-1, resume);
        Assert.assertEquals(resume, storage.getAll()[0]);
    }

    @Test
    public void deleteResume() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume_next = storage.getAll()[1];
        Method deleteResume = storage.getClass().getDeclaredMethod("deleteResume", int.class);
        deleteResume.invoke(storage, 0);
        Assert.assertEquals(resume_next, storage.getAll()[0]);
    }
}