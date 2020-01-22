package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 3, 30);
    public static final LocalDate EMPTY = LocalDate.of(4000, 3, 30);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String localDateToString(LocalDate localDate) {
        if (localDate.equals(EMPTY)) {
            return "";
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(localDate);
    }

    public static LocalDate stringToLocalDate(String localDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            if (localDate == null || localDate.equals("")) {
                return NOW;
            }
            return LocalDate.parse(localDate, dtf);
        } catch (RuntimeException e) {
            return NOW;
        }
    }

}
