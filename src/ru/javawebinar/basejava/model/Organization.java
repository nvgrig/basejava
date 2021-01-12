package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final Link homePage;
    private final List<CareerPeriod> careerPeriods = new ArrayList<>();

    public Organization(String linkName, String linkUrl, YearMonth beginDate, YearMonth finishDate, String title, String description) {
        this.homePage = new Link(linkName, linkUrl);
        careerPeriods.add(new CareerPeriod(beginDate, finishDate, title, description));
    }

    @Override
    public String toString() {
        return "Place{" +
                "homePage=" + homePage +
                ", careerPeriods=" + careerPeriods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization organization = (Organization) o;

        if (!homePage.equals(organization.homePage)) return false;
        return careerPeriods.equals(organization.careerPeriods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + careerPeriods.hashCode();
        return result;
    }
}