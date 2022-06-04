package com.htfp.service.cac.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/**
 * @Author sunjipeng
 * @Date 2022-06-04 17:35
 */
@Slf4j
public class DateUtils {
    public static final String DAY_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DIAGONAL_DAY_DATE_FORMAT = "yyyy/MM/dd";
    public static final String NORMAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DIAGONAL_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public static final String TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String HOUR_PATTERN = "HH:mm";
    public static final String HOURMINUTE_PATTERN = "HHmm";
    public static final String SECOND_PATTERN = "HH:mm:ss";
    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String DATE_PATTERN_yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String DATE_HOUR_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sssZ";
    public static final String DATETIME_ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String DATETIME_MSEC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.sss";
    public static final String DIAGONAL_DAY_PATTERN = "yyyy/MM/dd";
    public static final String DATETIME_FORMAT_PATTERN_SE = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String DIAGONAL_PATTERN_WITHOUT_SECOND = "yyyy/MM/dd HH:mm";
    public static final int DAY_IN_MILS = 86400000;

    public static Date getDateByStr(String dateStr, String pattern) {
        try {
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }
            if (StringUtils.isBlank(pattern)) {
                pattern = NORMAL_PATTERN;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("getDateByStr error, dateStr = " + dateStr + ", pattern = " + pattern, e);
        }
        return null;
    }

    public static Date getDateByStrCommon(String dateStr, String pattern) {
        try {
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }
            if (StringUtils.isBlank(pattern)) {
                pattern = NORMAL_PATTERN;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("getDateByStr error, dateStr = " + dateStr + ", pattern = " + pattern, e);
        }
        return null;
    }

    public static String getDateFormatString(Date date, String pattern) {
        try {
            if (StringUtils.isBlank(pattern)) {
                pattern = NORMAL_PATTERN;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            return sdf.format(date);
        } catch (Exception e) {
            log.error("getDateFormatString error", e);
        }
        return null;
    }

    public static String date2StrDay(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String getDateFormatString(long millisecond, String pattern) {
        return getDateFormatString(new Date(millisecond), pattern);
    }

    public static String date2StrDay(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    public static String date2Str24H(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(NORMAL_PATTERN);
        return dateFormat.format(date);
    }
    public static String date2Str(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
    public static String date2Str(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date dateConverDay(Date date, int afterDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, afterDay); // 往后推afterDay天
        return calendar.getTime();
    }

    public static Date getDateBefore(int daysBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daysBefore * -1);
        return cal.getTime();
    }

    public static Date getDateAfter(Date date, int daysAfter) {
        long dateMillSeconds = date.getTime() + 1000L * 3600 * 24 * daysAfter;
        return new Date(dateMillSeconds);
    }

    public static Date getDateBefore(Date date, int daysBefore) {
        long dateMillSeconds = date.getTime() - 1000L * 3600 * 24 * daysBefore;
        return new Date(dateMillSeconds);
    }

    public static Date getSecondAfter(Date date , int secondAfter) {
        long secondMillSeconds = date.getTime() + 1000L * secondAfter;
        return new Date(secondMillSeconds);
    }

    public static Date getMinuteAfter(Date date , int minuteAfter) {
        long hourMillSeconds = date.getTime() + 1000L * 60 * minuteAfter ;
        return new Date(hourMillSeconds);
    }


    public static Date today() {
        return toDay(new Date());
    }

    private static Date toDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getNormalDateString(Long time){
        if (time == null) {
            return null;
        }
        return new DateTime(time).toString(NORMAL_PATTERN);
    }

    public static int daysBetweenDate(Date endDate, Date startDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        endDate = sdf.parse(sdf.format(endDate));
        startDate = sdf.parse(sdf.format(startDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(startDate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time1-time2)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    public static int getDaysBetween(Date endDate, Date startDate) {
        //去掉具体时间秒
        LocalDate start = new DateTime(startDate).toLocalDate();
        LocalDate end = new DateTime(endDate).toLocalDate();
        return Days.daysBetween(start, end).getDays();
    }

    public static Date getHourAfter(Date date , int hourAfter) {
        long hourMillSeconds = date.getTime() + 1000L * 3600 * hourAfter ;
        return new Date(hourMillSeconds);
    }

    public static Date getHourBefore(Date date, int hourBefore){
        long hourMillSeconds = date.getTime() - 1000L * 3600 * hourBefore ;
        return new Date(hourMillSeconds);
    }

    /**
     * 判断日期是否是有效格式
     * @param date
     * @return
     */
    public static boolean checkValidDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return false;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date returnDate = sdf.parse(date.trim());
            return returnDate != null;
        } catch (Exception e) {
            log.error("日期转换出错！原因：" + e.getMessage(), e);
        }
        return false;
    }

    public static boolean isDateAfter(String startDateStr, String endDateStr, String pattern) {
        Date startDate = getDateByStr(startDateStr, pattern);
        Date endDate = getDateByStr(endDateStr, pattern);
        return startDate != null && endDate != null && !startDate.after(endDate);
    }

    //判断订单是否过期
    public static boolean isExpired(String travelDate) {
        String today = DateUtils.getDateFormatString(new Date(), DateUtils.DAY_DATE_FORMAT);
        return travelDate.compareTo(today) < 0;
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 不同格式的日期字符串转换
     * @param sourceDateString
     * @param sourcePattern
     * @param targetPattern
     * @return
     */
    public static String dateFormatConvert(String sourceDateString, String sourcePattern, String targetPattern) {
        try {
            if (StringUtils.isBlank(sourceDateString)) {
                return null;
            }
            if (StringUtils.isBlank(sourcePattern)) {
                sourcePattern = NORMAL_PATTERN;
            }
            if (StringUtils.isBlank(targetPattern)) {
                targetPattern = NORMAL_PATTERN;
            }

            SimpleDateFormat sourceSdf = new SimpleDateFormat(sourcePattern, Locale.US);
            SimpleDateFormat targetSdf = new SimpleDateFormat(targetPattern, Locale.US);
            return targetSdf.format(sourceSdf.parse(sourceDateString));
        } catch (ParseException e) {
            log.error("dateFormatConvert error, sourceDateString = {}, sourcePattern = {}, targetPattern = {}",
                    sourceDateString, sourcePattern, targetPattern, e);
        }
        return null;
    }

    public static Date dateFormatConvert(Date sourceDate, String sourcePattern, String targetPattern) {
        try {
            if (null == sourceDate) {
                return null;
            }
            if (StringUtils.isBlank(sourcePattern)) {
                sourcePattern = NORMAL_PATTERN;
            }
            if (StringUtils.isBlank(targetPattern)) {
                targetPattern = NORMAL_PATTERN;
            }

            SimpleDateFormat sourceSdf = new SimpleDateFormat(sourcePattern, Locale.US);
            SimpleDateFormat targetSdf = new SimpleDateFormat(targetPattern, Locale.US);
            return targetSdf.parse(sourceSdf.format(sourceDate));
        } catch (ParseException e) {
            log.error("dateFormatConvert error, sourceDate = {}, sourcePattern = {}, targetPattern = {}",
                    sourceDate, sourcePattern, targetPattern, e);
        }
        return null;
    }

    public static Integer getDateInt (Date date) {
        if (Objects.isNull(date)) {
            return 0;
        }
        String dateStr = getDateFormatString(date, DATE_PATTERN);
        return StringUtils.isBlank(dateStr) ? 0 : Integer.valueOf(dateStr);
    }

    public static boolean checkDateIsBeofreToday (Date date) {
        Date today = today();

        if(date.before(today)){
            return true;
        }

        return false;
    }
}
