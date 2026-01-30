package com.svalero.RosasTattoo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String UI_DATE_PATTERN = "dd-MM-yyyy";
    public static final String API_DATE_PATTERN = "yyyy-MM-dd";

    public static String toApiFormat(String uiDate) {
        if (uiDate == null || uiDate.trim().isEmpty()) {
            return "";
        }

        LocalDate date = LocalDate.parse(
                uiDate.trim(),
                DateTimeFormatter.ofPattern(UI_DATE_PATTERN)
        );

        return date.format(DateTimeFormatter.ofPattern(API_DATE_PATTERN));
    }

    public static String toUiFormat(String apiDate) {
        if (apiDate == null || apiDate.trim().isEmpty()) {
            return "";
        }

        LocalDate date = LocalDate.parse(
                apiDate.trim(),
                DateTimeFormatter.ofPattern(API_DATE_PATTERN)
        );

        return date.format(DateTimeFormatter.ofPattern(UI_DATE_PATTERN));
    }
}

