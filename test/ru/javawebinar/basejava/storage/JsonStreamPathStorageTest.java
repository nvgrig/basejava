package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.JsonStreamStrategy;

public class JsonStreamPathStorageTest extends AbstractStorageTest {

    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamStrategy()));
    }

}