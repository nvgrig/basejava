package ru.javawebinar.basejava.util;

public class HtmlView {
    public static String formatBrackets(String string) {
        return (string == null || string.equals("")) ? "" : "(" + string + ")";
    }

    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        } else return string.equals("") || string.matches("\\s+");
    }

    public static boolean isEmpty(String[] strings) {
        return  (strings == null || (strings.length == 1 && isEmpty(strings[0])));
    }
}
