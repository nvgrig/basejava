package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamStrategy implements SerializationStrategy {

    @Override
    public void strategyWrite(Resume resume, OutputStream outputStream) throws IOException {
        try(Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            JsonParser.writer(resume, writer);
        }
    }

    @Override
    public Resume strategyRead(InputStream inputStream) throws IOException {
        try(Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
