package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {

    void strategyWrite(Resume resume, OutputStream outputStream) throws IOException;

    Resume strategyRead(InputStream inputStream) throws IOException;
}
