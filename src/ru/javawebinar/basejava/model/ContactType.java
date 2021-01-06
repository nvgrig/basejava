package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
