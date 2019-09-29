package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.MAX_SIZE;
import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void sizeOverLoaded() {
        try {

            for (int i = 4; i <= MAX_SIZE; i++) {
                storage.save(new Resume("FIO_" + i));
            }
        } catch (StorageException e) {
            fail(e.getMessage());
        }
        storage.save(new Resume("FIO_" + MAX_SIZE + 1));
    }
}
