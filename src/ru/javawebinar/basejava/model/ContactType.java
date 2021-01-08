package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    WEBPAGE("Сайт:");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
