package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractPathStorage extends AbstractStorage {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not null");
        if (!Files.isDirectory(directory)||!Files.isWritable(directory) ) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        File file = (File) searchKey;
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Object file) {
        return ((File) file).exists();
    }

    @Override
    protected void doSave(Resume resume, Object file) {
        File f = ((File) file);
        try {

            f.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + f.getAbsolutePath(), f.getName(), e);
        }
        doUpdate(resume, f);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        File file = (File) searchKey;
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(Object searchKey) {
        File f = (File) searchKey;
        f.delete();
//удаляет файл
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        //читает все файлы и делает do Read и возвращает list
        List<Resume> resumes = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) {
            return resumes;
        }
        for (File f : directory.listFiles()) {
            resumes.add(doGet(f));
        }
        return resumes;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error",null);
        }
    }

    @Override
    public int size() {
        //количество фалов в каталоге
        File[] files = directory.listFiles();
        return files == null ? 0 : files.length;
    }
}
