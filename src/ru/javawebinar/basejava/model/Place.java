package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class Place {
    private YearMonth beginDate;
    private YearMonth finishDate;
    private String title;
    private String comments;

    public Place(YearMonth beginDate, YearMonth finishDate, String title, String comments) {
        this.beginDate = beginDate;
        this.finishDate = finishDate;
        this.title = title;
        this.comments = comments;
    }

    public Place(YearMonth beginDate, YearMonth finishDate, String title) {
        this(beginDate, finishDate, title,"");
    }
}
