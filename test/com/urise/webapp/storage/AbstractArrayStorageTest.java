package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.UUID;

public class AbstractArrayStorageTest {
    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static String UNIQ_UUID;
    private static int size;
    protected Storage storage;

    @Before
    public void setUp() {
        fillStorage(100);
        Resume[] arrResume = storage.getAll();
        while (true) {
            String uuid = UUID.randomUUID().toString();
            int i = 0;
            for (; i < storage.size(); i++) {
                if (uuid.equals(arrResume[i]))
                    break;
            }
            if (i == storage.size()) {
                UNIQ_UUID = uuid;
                break;
            }
        }
    }

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private int fillStorage(int quantityElements) {
        storage.clear();
        if (quantityElements <= 0) {
            storage.save(new Resume(UUID_1));
            storage.save(new Resume(UUID_2));
            storage.save(new Resume(UUID_3));
            return 3;
        }
        for (int i = 0; i < quantityElements; i++) {
            storage.save(new Resume("UUID_" + i));
        }
        size = quantityElements;
        return quantityElements;
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertArrayEquals(storage.getAll(), new Resume[storage.size()]);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(storage.getAll()[0]);
    }

    @Test
    public void saveNotExist() {
        int size = storage.size();
        storage.save(new Resume(UNIQ_UUID + "test"));
        Resume r = storage.get(UNIQ_UUID + "test");
        Assert.assertEquals(storage.size(), size + 1);
        Assert.assertNotEquals(r, null);
    }

    @Test
    public void getExist() {
        Assert.assertEquals(storage.get(UUID_1).getUuid(), UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UNIQ_UUID);
    }


    @Test
    public void deleteExist() {
        int size_before = storage.size();
        storage.delete(UUID_1);
        Assert.assertEquals(storage.size(), size_before - 1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UNIQ_UUID);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(storage.getAll().length, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(storage.size(), size);
    }

    @Test(expected = StorageException.class)
    public void sizeOverLoaded() throws NoSuchFieldException, IllegalAccessException {
        Field arr_size = storage.getClass().getSuperclass().getDeclaredField("MAX_SIZE");
        arr_size.setAccessible(true);
        Integer MAX_SIZE = (Integer) arr_size.get(storage);
        fillStorage(MAX_SIZE + 100);
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