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

public class PathStorage extends AbstractStorage<Path> {

    private Path pathDirectory;

    private ReadWriteStorage way;

    public PathStorage(String directory) {
        Path path = Paths.get(directory);
        Objects.requireNonNull(path, "pathDirectory must not be null");

        if (!Files.isDirectory(path) || !Files.isWritable(path) || !Files.isWritable(path)) {
            throw new IllegalArgumentException(directory + " is not directory or not readable/writable");
        }
        this.pathDirectory = path;
    }

    public void setWay(ReadWriteStorage way) {
        this.way = way;
    }

    private Resume readPathResume(InputStream in) throws IOException {
        return way.readFileResume(in);
    }

    private void writePathResume(Resume resume, OutputStream out) throws IOException {
        way.writeFileResume(resume, out);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void updateResume(Path path, Resume resume) {
        try {
            writePathResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error " + path.toString(), resume.getUuid(), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return readPathResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error" + path.toString(), null, e);
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
        Path pathResume = Paths.get(uuid);
        return pathDirectory.resolve(pathResume);
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
        List<Resume> list = new ArrayList<>();

        try {
            Files.list(pathDirectory).forEach(path -> list.add(getResume(path)));
        } catch (IOException e) {
            throw new StorageException("pathDirectory read error: " + pathDirectory.toString(), null);
        }

        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(pathDirectory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("pathDirectory clear error: " + pathDirectory.toString(), null);
        }
    }

    @Override
    public int size() {
        int length;
        try {
            length = Files.list(pathDirectory).toArray().length;
        } catch (IOException e) {
            throw new StorageException("pathDirectory read error: " + pathDirectory.toString(), null);
        }
        return length;
    }
}
