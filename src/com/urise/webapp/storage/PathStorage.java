package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage {
    private Path directory;
    private StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not null");
        this.streamSerializer=streamSerializer;
        if (!Files.isDirectory(directory)||!Files.isWritable(directory) ) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = directory;
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        Path file = (Path) searchKey;
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Object file) {
        Path path=(Path)file;
        return Files.isRegularFile(path);
    }

    @Override
    protected void doSave(Resume resume, Object file) {
        Path path = ((Path) file);
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.toString(), path.getFileName().toString(), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        Path path = (Path) searchKey;
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("File read error", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Object searchKey) {
        Path path = (Path) searchKey;
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File deleting error " + path.toString(),path.toString(),e);
        }
//удаляет файл
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        //читает все файлы и делает doRead и возвращает list
        List<Resume> resumes;
        try {
            resumes=Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Files read error", null,e);
        }
        return resumes;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("File delete error",null,e);
        }
    }

    @Override
    public int size() {
        //количество фалов в каталоге
        try {
            return (int)Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory file read error",null,e);
        }
    }
}
