package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;


import static com.urise.webapp.storage.AbstractArrayStorage.MAX_SIZE;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static final String UUID_4 = "UUID_4";
    private static final Resume R_1 = new Resume(UUID_1);
    private static final Resume R_2 = new Resume(UUID_2);
    private static final Resume R_4 = new Resume(UUID_4);
    protected Storage storage;
    private static final Resume R_3 = new Resume(UUID_3);

    protected AbstractStorageTest(Storage storage) {
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
        assertEquals(0, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R_3);
    }

    @Test
    public void saveNotExist() {
        int sizeBefore = storage.size();
        storage.save(R_4);
        assertEquals(storage.size(), sizeBefore + 1);
        assertEquals(R_4, storage.get(UUID_4));
    }

    @Test
    public void getExist() {
        assertEquals(storage.get(UUID_1), R_1);
        assertEquals(storage.get(UUID_2), R_2);
        assertEquals(storage.get(UUID_3), R_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("notExistableUUID");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExist() {
        int sizeBefore = storage.size();
        storage.delete(UUID_1);
        assertEquals(storage.size(), sizeBefore - 1);
        storage.get(UUID_1);

    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("notExistableUUID");
    }

    @Test
    public void getAll() {
        assertArrayEquals( new Resume[]{R_1, R_2, R_3},storage.getAll());
    }

    @Test
    public void size() {
        assertEquals(storage.size(), 3);
    }

    @Test(expected = AssertionError.class)
    public void sizeOverLoaded() {
        try {
            if (!(storage instanceof AbstractArrayStorage)) {
                throw new StorageException("Is not instance of AbstractArrayStorage",null );
            }
            for (int i = 4; i <= MAX_SIZE; i++) {
                storage.save(new Resume());
            }
            storage.save(new Resume());
        } catch (StorageException e) {
            fail();
        }

    }

    @Test
    public void updateExist() {
        Resume rBefore = storage.get(UUID_1);
        Resume rAfter = new Resume(UUID_1);
        storage.update(rAfter);
        assertNotSame(storage.get(UUID_1), rBefore);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume rAfter = new Resume("test");
        storage.update(rAfter);
    }
}