package ru.javawebinar.basejava.storage;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File("D:\\Java_basejava\\basejava\\storage"), new ObjectStreamStrategy()));
    }

}