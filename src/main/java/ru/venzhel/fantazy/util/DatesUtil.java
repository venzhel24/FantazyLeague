package ru.venzhel.fantazy.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

public class DatesUtil {
    public static LocalDate[] getDates(int day1, int day2, String month, int year) {
        String[] monthArr = month.split("-");
        int month1 = convertMonth(monthArr[0]);
        int month2 = monthArr.length > 1 ? convertMonth(monthArr[1]) : month1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        LocalDate[] dates = {
                LocalDate.parse(String.format("%02d %02d %04d", day1, month1, year), formatter),
                LocalDate.parse(String.format("%02d %02d %04d", day2, month2, year), formatter)
        };

        Arrays.sort(dates);
        return dates;
    }

    public static LocalDate getDate(int day, String month, int year) {
        int month_int = convertMonth(month);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        return LocalDate.parse(String.format("%02d %02d %04d", day, month_int, year), formatter);
    }

    public static int convertMonth(String mth) {
        switch (mth) {
            case "FEB" -> {
                return 2;
            }
            case "JAN" -> {
                return 1;
            }
            case "NOV" -> {
                return 11;
            }
            case "DEC" -> {
                return 12;
            }
            case "MAR" -> {
                return 3;
            }
            default -> {
                return 0;
            }
        }
    }
}
