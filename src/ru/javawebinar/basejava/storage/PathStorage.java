package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not a directory or is not writable");
        }
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.strategy = strategy;
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            strategy.strategyWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Can't create path +" + path, getFileName(path), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.strategyRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(path));
        }
    }

    @Override
    protected List<Resume> getAll() {
        return getListFiles().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected Path getSearchKey(Resume resume) {
        return directory.resolve(resume.getUuid());
    }

    @Override
    protected boolean isResumeExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    public void clear() {
        getListFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getListFiles().count();
    }

    public Stream<Path> getListFiles() {
        Stream<Path> listFiles;
        try {
            listFiles = Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
        return listFiles;
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
