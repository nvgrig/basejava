package ru.javawebinar.basejava.model;

public class TextSection extends ResumeSection{
    private String value;

    public TextSection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
