package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Place {
    private final Link homePage;

    private final YearMonth beginDate;
    private final YearMonth finishDate;
    private final String title;
    private final String description;

    public Place(String linkName, String linkUrl, YearMonth beginDate, YearMonth finishDate, String title, String description) {
        Objects.requireNonNull(beginDate, "beginDate must not be null");
        Objects.requireNonNull(finishDate, "finishDate must not be null");
        Objects.requireNonNull(title, "title  must not be null");
        this.homePage = new Link(linkName, linkUrl);
        this.beginDate = beginDate;
        this.finishDate = finishDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return homePage + "\n" +
                beginDate + " - " + finishDate + "\n" +
                title + "\n" +
                description + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (!homePage.equals(place.homePage)) return false;
        if (!beginDate.equals(place.beginDate)) return false;
        if (!finishDate.equals(place.finishDate)) return false;
        if (!title.equals(place.title)) return false;
        return description != null ? description.equals(place.description) : place.description == null;
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + beginDate.hashCode();
        result = 31 * result + finishDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}