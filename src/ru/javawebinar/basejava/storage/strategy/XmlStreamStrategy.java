package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamStrategy implements SerializationStrategy {
    private final XmlParser xmlParser;

    public XmlStreamStrategy() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Link.class,
                ListSection.class, TextSection.class, OrganizationSection.class, Organization.Position.class);
    }

    @Override
    public void strategyWrite(Resume resume, OutputStream outputStream) throws IOException {
        try(Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume strategyRead(InputStream inputStream) throws IOException {
        try(Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
