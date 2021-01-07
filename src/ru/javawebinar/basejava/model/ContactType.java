package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    WEBPAGE("Сайт:");

    private final String name;

    ContactType(String name) {
        this.name = name;
    }

    public String getTitle() {
        return name;
    }
}
