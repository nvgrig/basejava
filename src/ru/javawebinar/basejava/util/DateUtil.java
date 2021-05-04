package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month){
        return LocalDate.of(year, month,1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "по настоящее время" : date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
    }

    public static LocalDate parse(String date) {
        if (date == null || date.trim().length() == 0 || date.equals("по настоящее время")) return NOW;
        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yyyy"));
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
