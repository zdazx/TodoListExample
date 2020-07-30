package com.thoughtworks.todolistexample.repository.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String INVALID_TIME = "";

    public static String toStringTimestamp(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        Date date = dateFormat.parse(dateString);
        if (date == null) {
            return INVALID_TIME;
        }
        return String.valueOf(date.getTime());
    }
}
