package ru.javawebinar.basejava.model;

import java.util.List;

public class ListSection<T> extends ResumeSection{
    private List<T> value;

    public ListSection(List<T> value) {
        this.value = value;
    }

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "value=" + value +
                '}';
    }
}
