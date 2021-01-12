package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class CareerPeriod {
    private final YearMonth beginDate;
    private final YearMonth finishDate;
    private final String title;
    private final String description;

    public CareerPeriod(YearMonth beginDate, YearMonth finishDate, String title, String description) {
        Objects.requireNonNull(beginDate, "beginDate must not be null");
        Objects.requireNonNull(finishDate, "finishDate must not be null");
        Objects.requireNonNull(title, "title  must not be null");
        this.beginDate = beginDate;
        this.finishDate = finishDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "CareerPeriod{" +
                "beginDate=" + beginDate +
                ", finishDate=" + finishDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CareerPeriod that = (CareerPeriod) o;

        if (!beginDate.equals(that.beginDate)) return false;
        if (!finishDate.equals(that.finishDate)) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = beginDate.hashCode();
        result = 31 * result + finishDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
