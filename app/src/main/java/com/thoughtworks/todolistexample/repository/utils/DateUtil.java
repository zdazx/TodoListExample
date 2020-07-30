package com.thoughtworks.todolistexample.repository.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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

    public static String toDate(String timestamp) {
        if (Objects.isNull(timestamp) || timestamp.equals("")) {
            return "";
        }
        return new SimpleDateFormat("MM月dd日", Locale.CHINA).format(Long.parseLong(timestamp));
    }

    public static String toCurrentDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE,  dd", Locale.ENGLISH);
        return dateFormat.format(new Date()).concat("th");
    }

    public static String toCurrentMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
        return dateFormat.format(new Date());
    }
}
