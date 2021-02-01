package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.XmlStreamStrategy;

public class XmlStreamPathStorageTest extends AbstractStorageTest {

    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamStrategy()));
    }

}