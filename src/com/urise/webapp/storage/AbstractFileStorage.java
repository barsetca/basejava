package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }

        this.directory = directory;
    }

    @Override
    protected boolean Exist(File file) {
        return file.exists();
    }

    @Override
    protected void updateResume(File file, Resume resume) {
        try {
            writeFileResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return readFileResume(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void saveResume(File file, Resume resume) {
        try {
            file.createNewFile();
            writeFileResume(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected List<Resume> getCopyList() {
        List<Resume> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            try {
                list.add(readFileResume(file));
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return list;
    }

    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            file.delete();
        }

    }

    @Override
    public int size() {
        int count = 0;
        for (File file : directory.listFiles()) {
            count++;
        }
        return count;
    }

    protected abstract Resume readFileResume(File file) throws IOException;

    protected abstract void writeFileResume(Resume resume, File file) throws IOException;
}
