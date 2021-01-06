package ru.javawebinar.basejava.model;

import java.util.List;

public class ListSection<T> extends ResumeSection{
    private final List<T> value;

    public ListSection(List<T> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T val: value) {
            result.append(val);
            result.append("\n");
        }
        return result.toString();
    }
}
