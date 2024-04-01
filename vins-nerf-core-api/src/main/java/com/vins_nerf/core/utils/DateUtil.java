package com.vins_nerf.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
public class DateUtil {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String IMU_INFO_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String GMT_DATE_FORMAT = "EEE d MMM yyyy HH:mm:ss 'GMT'";
    public static final String AIMED_DEFAULT_TIMEZONE_NAME = "Asia/Shanghai";
    public static final String AIMED_GMT_TIMEZONE_NAME = "GMT";
    public static final Locale AIMED_DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;
    public static final Locale AIMED_GMT_LOCALE = Locale.US;
    public static final TimeZone AIMED_DEFAULT_TIMEZONE = TimeZone.getTimeZone(AIMED_DEFAULT_TIMEZONE_NAME);
    public static final TimeZone AIMED_GMT_TIMEZONE = TimeZone.getTimeZone(AIMED_GMT_TIMEZONE_NAME);

    static {
        System.setProperty("user.timezone", AIMED_DEFAULT_TIMEZONE_NAME);
    }

    public static Date parse(String dateStr, String... dateFormat) {
        if (StringUtil.isNullOrEmpty(dateStr)) return null;
        if (dateFormat.length == 0)
            dateFormat = new String[]{DEFAULT_DATE_FORMAT, DEFAULT_TIME_FORMAT, GMT_DATE_FORMAT};
        try {
            return DateUtils.parseDate(dateStr, AIMED_DEFAULT_LOCALE, dateFormat);
        } catch (ParseException e) {
            log.error(String.format("Fail to parse Date[%s] by Format[%s]", dateStr, dateFormat), e);
            return null;
        }
    }

    public static String format(Date date, String dateFormat) {
        return date == null ? null : DateFormatUtils.format(date, dateFormat, AIMED_DEFAULT_TIMEZONE, AIMED_DEFAULT_LOCALE);
    }

    public static String getDefaultDateTime(Date date) {
        return DateUtil.format(date, DEFAULT_TIME_FORMAT);
    }

    public static String getDefaultDate(Date date) {
        return DateUtil.format(date, DEFAULT_DATE_FORMAT);
    }

    public static String gmtFormat(Date date, String dateFormat) {
        return date == null ? null : DateFormatUtils.format(date, dateFormat, AIMED_GMT_TIMEZONE, AIMED_GMT_LOCALE);
    }

    public static String getGMTDateTime() {
        return DateUtil.gmtFormat(new Date(), GMT_DATE_FORMAT);
    }

    public static boolean isDateFormat(String dateStr, String dateFormat) {
        return StringUtil.isNullOrEmpty(dateStr) ? false : parse(dateStr, dateFormat) != null;
    }

    public static boolean isDateFormat(String dateStr) {
        return isDateFormat(dateStr, DEFAULT_TIME_FORMAT);
    }
}
