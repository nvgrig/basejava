package ru.javawebinar.basejava.util;

public class HtmlView {
    public static String formatBrackets(String string) {
        return (string == null || string.equals("")) ? "" : "(" + string + ")";
    }
}
