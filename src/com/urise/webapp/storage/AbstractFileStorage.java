package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory,"directory must not null");
        if (directory.isDirectory()){
            throw new IllegalArgumentException(directory.getAbsolutePath() +  " is not directory");
        }
        if (directory.canRead()||directory.canWrite()){
            throw new IllegalArgumentException(directory.getAbsolutePath() +  " is not readable/writable");
        }
        this.directory=directory;
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        //как doSave только в существующий
        File f = (File)searchKey;
        try {
            doWrite(resume,f);
        } catch (IOException e) {
            throw new StorageException("IO error", f.getName(), e);
        }
    }

    @Override
    protected boolean isExist(Object file) {
        return ((File)file).exists();
    }

    @Override
    protected void doSave(Resume resume, Object file) {
        File f =((File)file);
        try {
            f.createNewFile();
            doWrite (resume,f);
        } catch (IOException e) {
            throw new StorageException("IO error", f.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File f) throws IOException;

    @Override
    protected Resume doGet(Object searchKey) {
         return doRead((File)searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        File f= (File)searchKey;
        f.delete();
//удаляет файл
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory,uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        //читает все файлы и делает do Read и возвращает list
        List<Resume> resumes = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files==null){
            return resumes;
        }
            for (File f : directory.listFiles()) {
                resumes.add(doRead(f));
            }
        return resumes;
    }

    protected abstract Resume doRead(File f);

    @Override
    public void clear() {
        //получить все файлыиз каталога и удалить
        File[] files =directory.listFiles();
        if (files==null) {
            return;
        }
        for (File f: files) {
            f.delete();
        }
    }

    @Override
    public int size() {
        //количество фалов в каталоге
        File[] files = directory.listFiles();
        return files==null?0:files.length;
    }
}
