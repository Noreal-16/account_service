package org.account_movement_service.account_movement_service.infrastructure.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format: " + dateStr, e);
        }
    }
}
