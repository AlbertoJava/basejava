package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

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
        insertResume.invoke(storage, 5, resume);
        Assert.assertNotEquals(resume, storage.getAll()[0]);
    }

    @Test
    public void deleteResume() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume_next = storage.getAll()[1];
        Method insertResume = storage.getClass().getDeclaredMethod("deleteResume", int.class);
        insertResume.invoke(storage, 0);
        Assert.assertNotEquals(resume_next, storage.getAll()[0]);
    }
}