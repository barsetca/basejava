package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.strategy.ReadWriteStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {

    private Path pathDirectory;

    private ReadWriteStrategy strategy;

    public PathStorage(String directory) {
        Path path = Paths.get(directory);
        Objects.requireNonNull(path, "pathDirectory must not be null");

        if (!Files.isDirectory(path) || !Files.isWritable(path) || !Files.isWritable(path)) {
            throw new IllegalArgumentException(directory + " is not directory or not readable/writable");
        }
        this.pathDirectory = path;
    }

    public void setStrategy(ReadWriteStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void updateResume(Path path, Resume resume) {
        try {
            strategy.writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error " + path.toString(), resume.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return strategy.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path readResume error" + path.toString(), null, e);
        }
    }

    @Override
    protected void saveResume(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't creat Path: " + path.toString(), resume.getUuid(), e);
        }
        updateResume(path, resume);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return pathDirectory.resolve(uuid);
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error" + path.toString(), null);
        }
    }

    @Override
    protected List<Resume> getCopyList() {
        List<Resume> list = null;
        try {
            list = Files.list(pathDirectory).map(this::getResume).collect(Collectors.toList());
        } catch (IOException e) {
            readErrorException(e);
        }
        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(pathDirectory).forEach(this::deleteResume);
        } catch (IOException e) {
            readErrorException(e);
        }
    }

    @Override
    public int size() {
        long size = 0;
        try {
            size = Files.list(pathDirectory).count();
        } catch (IOException e) {
            readErrorException(e);
        }
        return (int) size;
    }

    private void readErrorException(IOException e) {
        throw new StorageException("pathDirectory read error: " + pathDirectory.toString(), null, e);
    }
}
