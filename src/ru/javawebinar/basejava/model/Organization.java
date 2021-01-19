package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Link homePage;
    private final List<Position> positions;

    public Organization(String linkName, String linkUrl, Position...positions) {
        this(new Link(linkName, linkUrl), Arrays.asList(positions));
    }
    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + ", " + positions + ")";
    }

    public static class Position implements Serializable{
        @Serial
        private static final long serialVersionUID = 1L;
        private final LocalDate beginDate;
        private final LocalDate finishDate;
        private final String title;
        private final String description;

        public Position(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Position(LocalDate beginDate, LocalDate finishDate, String title, String description) {
            Objects.requireNonNull(beginDate, "beginDate must not be null");
            Objects.requireNonNull(finishDate, "finishDate must not be null");
            Objects.requireNonNull(title, "title  must not be null");
            this.beginDate = beginDate;
            this.finishDate = finishDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getBeginDate() {
            return beginDate;
        }

        public LocalDate getFinishDate() {
            return finishDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position that = (Position) o;

            if (!beginDate.equals(that.beginDate)) return false;
            if (!finishDate.equals(that.finishDate)) return false;
            if (!title.equals(that.title)) return false;
            return Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            int result = beginDate.hashCode();
            result = 31 * result + finishDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Position("+ beginDate + ", " + finishDate + ", " + title + ", " + description + ")";
        }
    }
}