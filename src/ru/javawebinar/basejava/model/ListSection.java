package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection<T> extends AbstractSection {
    private final List<T> value;

    public ListSection(List<T> value) {
        Objects.requireNonNull(value, "Place/text must not be null");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection<?> that = (ListSection<?>) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
