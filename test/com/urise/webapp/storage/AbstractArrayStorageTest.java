package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static final String UUID_4 = "UUID_4";
    private static final Resume R_1 = new Resume(UUID_1);
    private static final Resume R_2 = new Resume(UUID_2);
    private static final Resume R_3 = new Resume(UUID_3);
    private static final Resume R_4 = new Resume(UUID_4);
    protected Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    /*создается перед каждым методом*/
    @Before
    public void setUp() {
        storage.clear();
        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R_3);
    }

    @Test
    public void saveNotExist() {
        int size = storage.size();
        storage.save(R_4);
        Assert.assertEquals(storage.size(), size + 1);
        Assert.assertEquals(R_4, storage.get(UUID_4));
    }

    @Test
    public void getExist() {
        Assert.assertEquals(storage.get(UUID_1), R_1);
        Assert.assertEquals(storage.get(UUID_2), R_2);
        Assert.assertEquals(storage.get(UUID_3), R_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("notExistableUUID");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExist() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        Assert.assertEquals(storage.size(), sizeBefore - 1);
        storage.get(UUID_1);

    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("notExistableUUID");
    }

    @Test
    public void getAll() {
        Resume[] arrResume = storage.getAll();
        Assert.assertEquals(3, arrResume.length);
        Assert.assertEquals(R_1, arrResume[0]);
        Assert.assertEquals(R_2, arrResume[1]);
        Assert.assertEquals(R_3, arrResume[2]);
    }

    @Test
    public void size() {
        Assert.assertEquals(storage.size(), 3);
    }

    @Test(expected = StorageException.class)
    public void sizeOverLoaded() throws NoSuchFieldException, IllegalAccessException {
        Field arr_size = storage.getClass().getSuperclass().getDeclaredField("MAX_SIZE");
        arr_size.setAccessible(true);
        Integer MAX_SIZE = (Integer) arr_size.get(storage);
        try {
            for (int i = 4; i <= MAX_SIZE; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test
    public void updateExist() {
        Resume r_before = storage.getAll()[0];
        Resume r_after = new Resume(storage.getAll()[0].getUuid());
        storage.update(r_after);
        Assert.assertFalse(storage.getAll()[0] == r_before);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume r_after = new Resume("test");
        storage.update(r_after);
    }

}