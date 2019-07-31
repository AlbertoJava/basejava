package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Test
    public void getIndex() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String uuid = storage.getAll()[0].getUuid();
        Method getInd = storage.getClass().getDeclaredMethod("getIndex", String.class);
        Assert.assertEquals(0, (int) getInd.invoke(storage, uuid));
    }

    @Test
    public void insertResume() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume = new Resume("test");
        Method insertResume = storage.getClass().getDeclaredMethod("insertResume", int.class, Resume.class);
        insertResume.invoke(storage, 5, resume);
        Assert.assertNotEquals(resume, storage.getAll()[5]);
    }

    @Test
    public void deleteResume() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume = storage.getAll()[0];
        Method insertResume = storage.getClass().getDeclaredMethod("deleteResume", int.class);
        insertResume.invoke(storage, 0);
        Assert.assertNotEquals(resume, storage.getAll()[0]);
    }
}
