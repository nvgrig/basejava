package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    LINKEDIN("LinkedIn:"),
    GITHUB("GitHub:"),
    STACKOVERFLOW("StackOverflow:"),
    WEBSITE("Homepage:");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
