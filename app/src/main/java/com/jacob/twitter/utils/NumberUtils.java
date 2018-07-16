package com.jacob.twitter.utils;

public class NumberUtils {
    private static final String EMPTY = "   ";


    public static String ellipsize(int number) {
        if (number == 0) {
            return EMPTY;
        } else if (number < 1000) {
            return String.valueOf(number);
        } else if (number % 1000 != 0) {
            int thousands = number / 1000;
            return String.valueOf(thousands + "k");
        } else {
            int billions = number / 1000_000;
            return String.valueOf(billions + "M");
        }
    }
}
