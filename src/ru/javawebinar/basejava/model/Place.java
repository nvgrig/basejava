package ru.javawebinar.basejava.model;

import java.time.YearMonth;

public class Place {
    private YearMonth beginDate;
    private YearMonth finishDate;
    private String title;
    private String comments;

    public Place(String title, YearMonth beginDate, YearMonth finishDate, String comments) {
        this.title = title;
        this.beginDate = beginDate;
        this.finishDate = finishDate;
        this.comments = comments;
    }

    public Place(String title, YearMonth beginDate, YearMonth finishDate) {
        this(title, beginDate, finishDate, "");
    }

    @Override
    public String toString() {
        return title + "\n" +
                beginDate + " - " + finishDate + "\n" +
                comments + "\n";
    }

    public static void main(String[] args) {
        Place place1 = new Place("Coursera", YearMonth.of(2013, 3), YearMonth.of(2013, 5), "Functional Programming Principles in Scala by Martin Odersky");
        System.out.println(place1);
        Place place2 = new Place("Luxoft (Deutsche Bank)", YearMonth.of(2012, 10), YearMonth.of(2012, 4), "Ведущий программист\n" +
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        System.out.println(place2);
    }
}