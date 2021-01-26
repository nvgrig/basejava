package ru.javawebinar.basejava.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage("D:\\Java_basejava\\basejava\\storage", new ObjectStreamStrategy()));
    }

}