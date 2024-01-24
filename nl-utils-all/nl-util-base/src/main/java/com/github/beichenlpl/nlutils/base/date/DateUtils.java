package com.github.beichenlpl.nlutils.base.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author beichenlpl
 * @since 2023/09/04
 */
public class DateUtils {

    public static final String YEAR_2_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String YEAR_2_SECONDS = "yyyy-MM-dd HH:mm:ss";

    public static final String YEAR_2_DAY = "yyyy-MM-dd";

    public static final String YEAR_4 = "yyyy";

    public static final String YEAR_2 = "yy";

    public static final String MONTH = "MM";

    public static final String DAY = "dd";

    public static final String HOUR = "HH";

    public static final String MINUTE = "mm";

    public static final String SECONDS = "ss";

    public static final String MILLISECOND = "SSS";

    public static Date getNow() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getYesterday() {
        return getNowBeforeOrAfter(-1);
    }

    public static Date getTomorrow() {
        return getNowBeforeOrAfter(1);
    }

    public static Date getNowBeforeOrAfter(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getNow());
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static Date dateParse(String dateString, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateString);
    }

    public static String dateFormat(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getYear4() {
        return getYear4(getNow());
    }

    public static String getYear4(Date date) {
        return dateFormat(date, YEAR_4);
    }

    public static String getYear2() {
        return getYear2(getNow());
    }

    public static String getYear2(Date date) {
        return dateFormat(date, YEAR_2);
    }

    public static String getYear2Millisecond() {
        return getYear2Millisecond(getNow());
    }

    public static String getYear2Millisecond(Date date) {
        return dateFormat(date, YEAR_2_MILLISECOND);
    }

    public static String getYear2Seconds() {
        return getYear2Seconds(getNow());
    }

    public static String getYear2Seconds(Date date) {
        return dateFormat(date, YEAR_2_SECONDS);
    }

    public static String getYear2Day() {
        return getYear2Day(getNow());
    }

    public static String getYear2Day(Date date) {
        return dateFormat(date, YEAR_2_DAY);
    }

    public static String getMonth() {
        return getMonth(getNow());
    }

    public static String getMonth(Date date) {
        return dateFormat(date, MONTH);
    }

    public static String getDay() {
        return getDay(getNow());
    }

    public static String getDay(Date date) {
        return dateFormat(date, DAY);
    }

    public static String getHour() {
        return getHour(getNow());
    }

    public static String getHour(Date date) {
        return dateFormat(date, HOUR);
    }

    public static String getMinute() {
        return getMinute(getNow());
    }

    public static String getMinute(Date date) {
        return dateFormat(date, MINUTE);
    }

    public static String getSeconds() {
        return getSeconds(getNow());
    }

    public static String getSeconds(Date date) {
        return dateFormat(date, SECONDS);
    }

    public static String getMillisecond() {
        return getMillisecond(getNow());
    }

    public static String getMillisecond(Date date) {
        return dateFormat(date, MILLISECOND);
    }
}
