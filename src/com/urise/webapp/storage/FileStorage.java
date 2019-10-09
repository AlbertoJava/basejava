package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage {
    private File directory;
    private StreamSerializer streamSerializer;

    protected FileStorage(File directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "directory must not null");
        this.streamSerializer = streamSerializer;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }


    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        File file = (File) searchKey;
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
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
        for (File f : files) {
            resumes.add(doGet(f));
        }
        return resumes;
    }

    @Override
    public void clear() {
        //получить все файлыиз каталога и удалить
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            f.delete();
        }
    }

    @Override
    public int size() {
        //количество фалов в каталоге
        File[] files = directory.listFiles();
        return files == null ? 0 : files.length;
    }
}
