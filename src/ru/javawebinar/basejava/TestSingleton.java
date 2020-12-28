package ru.javawebinar.basejava;

public class TestSingleton {
    private static TestSingleton ourInstance;

    public static TestSingleton getOurInstance() {
        if (ourInstance == null) {
            ourInstance = new TestSingleton();
        }
        return ourInstance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getOurInstance().toString();
    }

}
