package util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

    public static final String format_YYYY = "yyyy";
    public static final String format_YYYYMM = "yyyyMM";
    public static final String format_YYYY_MM = "yyyy-MM";
    public static final String format_YYYYMMDD = "yyyyMMdd";
    public static final String format_YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String format_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String format_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String format_HHMMSS = "HHmmss";
    public static final String format_HHMM = "HHmm";
    public static final String format_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    static final String format_YYYY_MM_DDHH_MM_SS = "yyyy-MM-ddHH:mm:ss";
    public static final String format_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String format_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String format_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String format_YYYY_MM_DD_cn = "yyyy年MM月dd日";
    public static final String format_HH_MM_SS = "HH:mm:ss";
    public static final String format_HH_MM = "HH:mm";
    public static final String format_HH = "HH";
    public static final String format_MM = "MM";
    public static final String format_MM_DD = "MM/dd";
    public static final String format_YYYYMM2 = "yyyy/MM";
    public static final String format_YYYYMMDD2 = "yyyy/MM/dd";
    public static final String format_YYYYMMDD3 = "yyyy.MM.dd";
    public static final String format_YYYYMMDDHHMMSS2 = "yyyy/MM/dd HH:mm:ss";
    public static final String format_YYYYMMDDHHMM2 = "yyyy/MM/dd HH:mm";

    /**
     * <获取当前系统时间字符串>
     *
     * @return 格式：yyyy
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat(format_HH);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static String getCurrentYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY_MM);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * <获取当前系统时间字符串>
     *
     * @return 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY_MM_DD_HH_MM_SS);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * <获取指定格式的当前系统时间>
     *
     * @param format 指定格式
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * <日期转为字符串格式>
     *
     * @param date 日期
     * @return 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String dateToStr(Date date) {
        try {
            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY_MM_DD_HH_MM_SS);
                return sdf.format(date);
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * <日期转为字符串格式>
     *
     * @param date 日期
     * @param format 格式
     */
    public static String dateToStr(Date date, String format) {
        try {
            if (date != null) {
                if (StringUtils.isEmpty(format)) {
                    format = format_YYYY_MM_DD_HH_MM_SS;
                }
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(date);
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * <字符串格式转为日期>
     *
     * @param dateStr 日期字符串
     * @return 格式：yyyy-MM-dd HH:mm:ss
     */
    public static Date strToDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY_MM_DD_HH_MM_SS);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("dateToStr({}) error. case:{}", dateStr, e.getMessage());
        }
        return null;
    }

    /**
     * <获取默认格式[yyyy-MM-dd]的日期类型对象>
     */
    public static Date getDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY_MM_DD);
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            log.info("getDate error. case:{} ", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取格式为yyyy-MM-dd HH:mm:ss的日期类型对象
     */
    public static Date getDateToMinute() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY_MM_DD_HH_MM_SS);
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            log.info("getDateToMinute error. case:{} ", e.getMessage(), e);
        }
        return null;
    }

    /**
     * <获取指定格式的日期类型对象>
     */
    private static Date getDate(String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            log.info("getDate({}) error. case:{} ", format, e.getMessage(), e);
        }
        return null;
    }

    /**
     * <转Date>
     */
    public static Date getDate(String format, long mills) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date();
            date.setTime(mills);
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            log.info("getDate({},{}) error. case:{} ", format, mills, e.getMessage(), e);
        }
        return null;
    }

    /**
     * <字符串格式转为日期>
     *
     * @param dateStr 日期字符串
     * @param format 格式
     */
    public static Date strToDate(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("strToDate({}, {}) error. case:{}", dateStr, format, e.getMessage());
        }
        return null;
    }

    /**
     * 校验并返回符合format的日期字符串
     *
     * @param dateStr 日期字符串
     * @param format 格式
     */
    public static LocalDateTime getCheckedFormatDateStr(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(dateStr, df);
        } catch (Exception e) {
            log.error("getCheckedFormatDateStr({}, {}) error. case:{}", dateStr, format,
                    e.getMessage());
        }
        return null;
    }

    public static String getLastDayByYearMonth(String dateStr) {
        try {
            return dateToStr(getLastDayOfMonth(strToDate(dateStr, format_YYYY_MM_DD)),
                    format_YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            log.error("getLastDayByYearMonth({}) error. case:{}", dateStr, e.getMessage());
        }
        return null;
    }

    /**
     * String转LocalDateTime
     *
     * @param dateStr 日期字符串
     * @param format 格式
     */
    public static LocalDateTime strToLocalDateTime(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateStr, df).atStartOfDay();
        } catch (Exception e) {
            log.error("getCheckedFormatDateStr({}, {}) error. case:{}", dateStr, format,
                    e.getMessage());
        }
        return null;
    }

    /**
     * <字符串格式转为日期时间戳>
     *
     * @param dateStr 日期字符串
     * @param format 格式
     */
    public static long strToDateTimeStamp(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr)) {
            return 0L;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            log.error("strToDateTimeStamp({}, {}) error. case:{}", dateStr, format, e.getMessage());
        }
        return 0L;
    }

    /**
     * <日期转换成特定字符串>
     * 今天：今天 HH:mm
     * 昨天：昨天 HH:mm
     * 前天及以前展示年月日：yyyy.MM.dd
     *
     * @return 是返回true，不是返回false
     */
    public static String dateToSpecialStr(Date date) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format_HH_MM);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
        //当前时间
        Date now = new Date();
        //获取今天的日期
        String nowDay = sf.format(now);
        //获取昨天的日期
        String yDay = sf.format(now.getTime() - 24 * 60 * 60 * 1000);
        //对比的时间
        String day = sf.format(date);
        if (day.equals(nowDay)) {
            String format = sdf.format(date);
            str = "今天 " + format;
        } else if (day.equals(yDay)) {
            String format = sdf.format(date);
            str = "昨天 " + format;
        } else {
            str = sf.format(date);
        }
        return str;
    }

    /**
     * <日期转换成特定字符串>
     * 今天：今天 HH:mm
     * 昨天：昨天 HH:mm
     * 前天及以前展示年月日：yyyy.MM.dd
     *
     * @return 是返回true，不是返回false
     */
    public static String dateToZHStr(Date date) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format_HH_MM);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        //当前时间
        Date now = new Date();
        //获取今天的日期
        String nowDay = sf.format(now);
        //获取昨天的日期
        String yDay = sf.format(now.getTime() - 24 * 60 * 60 * 1000);
        //对比的时间
        String day = sf.format(date);
        if (day.equals(nowDay)) {
            String format = sdf.format(date);
            str = "今天 " + format;
        } else if (day.equals(yDay)) {
            String format = sdf.format(date);
            str = "昨天 " + format;
        } else {
            str = sf.format(date);
        }
        return str;
    }

    /**
     * <日期转换成特定字符串>
     * 今天：今天 HH:mm
     * 昨天：昨天 HH:mm
     * 前天及以前展示年月日：yyyy.MM.dd
     *
     * @return 是返回true，不是返回false
     */
    public static String dateToENStr(Date date) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format_HH_MM);
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        //当前时间
        Date now = new Date();
        //获取今天的日期
        String nowDay = sf.format(now);
        //获取昨天的日期
        String yDay = sf.format(now.getTime() - 24 * 60 * 60 * 1000);
        //对比的时间
        String day = sf.format(date);
        if (day.equals(nowDay)) {
            String format = sdf.format(date);
            str = "today " + format;
        } else if (day.equals(yDay)) {
            String format = sdf.format(date);
            str = "yesterday " + format;
        } else {
            str = sf.format(date);
        }
        return str;
    }

    /**
     * <通过时间秒毫秒数判断两个时间的间隔的天数>
     */
    private static int differentDays(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        }
        return -1;
    }

    /**
     * <通过时间秒毫秒数判断两个时间的间隔的小时数>
     */
    public static int differentHour(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600));
        }
        return -1;
    }

    /**
     * 判断传入的时间是否在当前时间之后
     */
    public static boolean isAfter(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date parse1 = sf.parse(sf.format(date));
            Date now = sf.parse(sf.format(new Date()));
            if (parse1.getTime() - now.getTime() < 0) {
                return false;
            }
        } catch (ParseException e) {
            log.info("isAfter({}), case: {} ", date, e.getMessage(), e);
        }
        return true;
    }

    /**
     * 获取传入时间的季度开始时间
     */
    public static Date getQuarterStartTime(Date paramDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(paramDate);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date startDay = null;
        try {
            if (currentMonth <= 3) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth <= 6) {
                c.set(Calendar.MONTH, 3);
            } else if (currentMonth <= 9) {
                c.set(Calendar.MONTH, 6);
            } else if (currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
            }
            c.set(Calendar.DATE, 1);

            Date date = c.getTime();

            String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);

            startDay = DateUtils.strToDate(str + " 00:00:00", DateUtils.format_YYYY_MM_DD_HH_MM_SS);

        } catch (Exception e) {
            log.info("getQuarterStartTime({}), case: {} ", paramDate, e.getMessage(), e);
        }
        return startDay;
    }

    /**
     * 处理返回给前端时，TIMESTAMP格式为.000+0000的问题
     */
    public static String formatTimestamp(Date date) {
        if (date != null) {
            try {
                return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {}
        }
        return null;
    }

    /**
     * 获取传入时间的季度结束时间
     */
    public static Date getQuarterEndTime(Date paramDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(paramDate);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date startDay = null;
        try {
            if (currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
            } else if (currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
            } else if (currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
            } else if (currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
            }
            int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            c.set(Calendar.DATE, MaxDay);

            Date date = c.getTime();

            String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);

            startDay = DateUtils.strToDate(str + " 23:59:59", DateUtils.format_YYYY_MM_DD_HH_MM_SS);

        } catch (Exception e) {
            log.info("getQuarterEndTime({}), case: {} ", paramDate, e.getMessage(), e);
        }
        return startDay;
    }

    /**
     * 获取N小时后的时间
     */

    public static Date getNHourLaterTime(Date date, int n) {
        long ms = n * 3600 * 1000L;
        return new Date(date.getTime() + ms);
    }

    /**
     * 获取过去 第 past 天的日期
     *
     * @return YYYY_MM_DD
     */
    private static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        return DateUtils.dateToStr(today, DateUtils.format_YYYY_MM_DD);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取传入日期的下周一至周末的时间
     *
     * @return yyyy-MM-dd 格式的时间列表
     */
    public static List<String> getNextWeek(Date date) {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int offset;
        //下周一
        offset = dayOfWeek == 1 ? 1 : 9 - dayOfWeek;
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        dates.add(df.format(calendar.getTime()));
        //下周二
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dates.add(df.format(calendar.getTime()));
        //下周三
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dates.add(df.format(calendar.getTime()));
        //下周四
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dates.add(df.format(calendar.getTime()));
        //下周五
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dates.add(df.format(calendar.getTime()));
        //下周六
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dates.add(df.format(calendar.getTime()));
        //下周日
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        dates.add(df.format(calendar.getTime()));
        return dates;
    }

    /**
     * 获取昨天0点0分0秒
     *
     * @return 日期
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据时间获取下一个月的今天
     *
     * @param date 日期
     * @return 下个雨的日期
     */
    public static Date getNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前月最后一天
     */
    private static Date getLastDayOfMonth(Date paramDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(paramDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = ca.getTime();
        String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
        return DateUtils.strToDate(str + " 23:59:59", DateUtils.format_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前月最后一天
     */
    public static String getLastDayOfMonthStr(Date paramDate, String format) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(paramDate);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = ca.getTime();
        String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
        return dateToStr(
                DateUtils.strToDate(str + " 23:59:59", DateUtils.format_YYYY_MM_DD_HH_MM_SS),
                format);
    }

    /**
     * 获取某月最后一天
     */
    public static Date getLastDayOfNMonth(Date paramDate, int n) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(paramDate);
        ca.add(Calendar.MONTH, n);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = ca.getTime();
        String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
        return DateUtils.strToDate(str + " 23:59:59", DateUtils.format_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前月第一天
     */
    private static Date getFirstDayOfMonth(Date paramDate) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(paramDate);
        ca.add(Calendar.MONTH, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        Date date = ca.getTime();
        String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
        return DateUtils.strToDate(str + " 00:00:00", format_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前月第一天：YYYY_MM_DD
     */
    public static String getFirstDayOfThisMonth() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        Date date = ca.getTime();
        return DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
    }

    /**
     * 获取n个月前
     */
    public static Date getLastNMonth(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -n);
        return c.getTime();
    }

    /**
     * 获取前/后半年的开始时间
     */
    public static Date getHalfYearStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date startDay = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            Date date = c.getTime();
            String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
            startDay = DateUtils.strToDate(str + " 00:00:00", DateUtils.format_YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            log.info("getHalfYearStartTime(), case: {} ", e.getMessage(), e);
        }
        return startDay;

    }

    /**
     * 获取前/后半年的结束时间
     */
    public static Date getHalfYearEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date endDay = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            Date date = c.getTime();
            String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
            endDay = DateUtils.strToDate(str + " 23:59:59", DateUtils.format_YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            log.info("getHalfYearEndTime(), case: {} ", e.getMessage(), e);
        }
        return endDay;
    }

    /**
     * 当前年的开始时间，即2012-01-01 00:00:00
     */
    public static Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date startDay = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            Date date = c.getTime();
            String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
            startDay = DateUtils.strToDate(str + " 00:00:00", DateUtils.format_YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            log.info("getCurrentYearStartTime(), case: {} ", e.getMessage(), e);
        }
        return startDay;
    }

    /**
     * 当前年的结束时间，即2012-12-31 23:59:59
     */
    public static Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date endDay = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            Date date = c.getTime();
            String str = DateUtils.dateToStr(date, DateUtils.format_YYYY_MM_DD);
            endDay = DateUtils.strToDate(str + " 23:59:59", DateUtils.format_YYYY_MM_DD_HH_MM_SS);
        } catch (Exception e) {
            log.info("getCurrentYearEndTime(), case: {} ", e.getMessage(), e);
        }
        return endDay;
    }

    /**
     * 得到当前时间是所在年度是第几周
     *
     * @return Integer 周的序号
     */
    public static Integer getSeqWeek(Calendar calendar) {
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Integer week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 获取两个日期之间的所有日期的集合，闭区间
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return List<String> list集合
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                list.add(sdf.format(startDate));
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, 1);
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            log.info("getBetweenDate({},{}), case: {} ", startTime, endTime, e.getMessage(), e);
        }
        return list;
    }

    /**
     * 根据Date获取年份
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int y = cal.get(Calendar.YEAR);
        return y;
    }

    /**
     * 根据Date获取季度
     */
    public static int getQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int m = cal.get(Calendar.MONTH) + 1;
        int quarter = 0;
        if (m >= 1 && m <= 3) {
            quarter = 1;
        }
        if (m >= 4 && m <= 6) {
            quarter = 2;
        }
        if (m >= 7 && m <= 9) {
            quarter = 3;
        }
        if (m >= 10 && m <= 12) {
            quarter = 4;
        }
        return quarter;
    }

    public static Date getByMonthAndDateNumber(int monthIndex, int dateIndex, Date now) {
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, monthIndex);
        c.set(Calendar.DATE, dateIndex);
        return c.getTime();
    }

    /**
     * 根据Date获取月份
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * date转换为自定义日期格式
     */
    public static Date getDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            log.info("getBetweenDate({},{}), case: {} ", date, format, e.getMessage(), e);
        }
        return null;
    }

    /**
     * date2比date1多的天数
     */
    public static int differentDaysByYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            //非同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                //闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else {
                    //非闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1 + 1);
        } else {
            //同一年
            return day2 - day1 + 1;
        }
    }

    /**
     * 获取上月的yearMonth
     */
    public static String getLastYearMonth(String yearMonth) {
        Date now = strToDate(yearMonth, format_YYYY_MM);
        if (now == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -1);
        return dateToStr(c.getTime(), format_YYYY_MM);
    }

    /**
     * 检验是否为日期类型的字符串
     */
    public static boolean isValidDate(String input, String format) {
        if (StringUtils.isEmpty(input)) {
            return true;
        }
        boolean valid = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String output = dateFormat.format(dateFormat.parse(input));
            valid = true;
        } catch (Exception ignore) {}
        return valid;
    }

    /**
     * 特定日期是否在两个日期之间
     *
     * @param date 特定日期
     * @param startDate 开始
     * @param endDate 结束
     */
    public static boolean between(Date date, Date startDate, Date endDate) {
        if (date == null || startDate == null || endDate == null) {
            return false;
        }
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    /**
     * 获取本年的年月
     *
     * @param date 日期
     */
    public static String getThisYearMonth(Date date, SimpleDateFormat df, Integer amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, amount);
        if (df != null) {
            return df.format(date);
        }
        return new SimpleDateFormat(format_YYYY_MM).format(date);
    }

    /**
     * 获取特定日期的上个月16号0点0分0秒
     *
     * @param date 特定日期
     * @return 上个月16号0点0分0秒
     */
    public static Date get16OfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取特定日期当月15号23点59分59秒
     *
     * @param date 特定日期
     * @return 当月15号23点59分59秒
     */
    public static Date get15OfThisMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取特定日期的上个月16号0点0分0秒，获取特定日期的当月/上个月n号n点n分n秒
     *
     * @param date 特定日期
     * @param amount 月：上个月-1， 本月为0，下个月+1
     * @param dayOfMonth 某一天
     * @param hour 特定小时
     * @param minute 特定分钟
     * @param second 特定秒
     * @return 特定日期的特定时间
     */
    public static Date getTimeMonth(Date date, int amount, int dayOfMonth, int hour, int minute,
            int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 判断strDate字符串是否为strDate所在月份的第一天，
     *
     * @param strDate 待判断的日期字符串
     * @param dateFormat strDate的日期格式
     */
    public static boolean isFirstDayOfMonth(String strDate, String dateFormat) {
        if (StringUtils.isBlank(strDate)) {
            return false;
        }
        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = format_YYYY_MM_DD;
        }
        Date inputDate = DateUtils.strToDate(strDate, dateFormat);
        Date firstDate = getFirstDayOfMonth(inputDate);
        strDate = DateUtils.dateToStr(inputDate, dateFormat);
        return DateUtils.dateToStr(firstDate, dateFormat).equals(strDate);
    }

    /**
     * 判断strDate字符串是否为strDate所在月份的最后一天，
     *
     * @param strDate 待判断的日期字符串
     * @param dateFormat strDate的日期格式
     */
    public static boolean isLastDayOfMonth(String strDate, String dateFormat) {
        if (StringUtils.isBlank(strDate)) {
            return false;
        }
        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = format_YYYY_MM_DD;
        }
        Date inputDate = DateUtils.strToDate(strDate, dateFormat);
        Date firstDate = getLastDayOfMonth(inputDate);
        strDate = DateUtils.dateToStr(inputDate, dateFormat);
        return DateUtils.dateToStr(firstDate, dateFormat).equals(strDate);
    }

    /**
     * 获取两个日期之间的所有月份最后一天的集合，闭区间
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return List<String> list集合
     */
    public static List<String> getLastDayOfBetweenMonth(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(format_YYYY_MM_DD);
        List<String> list = new ArrayList<>();
        try {
            Date startDate = getLastDayOfMonth(sdf.parse(startTime));
            Date endDate = getLastDayOfMonth(sdf.parse(endTime));
            if (startDate == null || endDate == null) {
                return list;
            }
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                list.add(sdf.format(getLastDayOfMonth(startDate)));
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, 1);
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            log.info("getLastDayOfBetweenMonth({},{}), case: {} ", startTime, endTime,
                    e.getMessage());
        }
        return list;
    }

    /**
     * FF
     * 校验开始日期<=截止日期
     */
    public static boolean checkStartDateEndDate(String startDate, String endDate, String format) {
        if (StringUtils.isBlank(format)) {
            format = format_YYYY_MM_DD;
        }
        if (!StringUtils.isBlank(startDate) && !StringUtils.isBlank(endDate)) {
            return Objects.requireNonNull(strToDate(startDate, format)).getTime() <= Objects
                    .requireNonNull(strToDate(endDate, format)).getTime();
        }
        return true;
    }

    public static String localDateTimeToStr(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return null;
        }
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
            return df.format(localDateTime);
        } catch (Exception e) {
            log.error("localDateTimeToStr({}, {}) error. case:{}", localDateTime, format,
                    e.getMessage());
        }
        return null;

    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            log.error("sleep exception", e);
        }
    }

    public static double getDurationByDateStr(String startDate, String endDate) {
        //总天数=最晚时间-最早时间+1
        return StringUtils.isBlank(startDate)
                       || StringUtils.isBlank(endDate) ? 0 : (double) DateUtils.differentDays(
                DateUtils.strToDate(startDate, DateUtils.format_YYYY_MM_DD),
                DateUtils.strToDate(endDate, DateUtils.format_YYYY_MM_DD)) + 1;
    }

    public static double getDurationByDateStr(String startDate, String endDate, String format) {
        //总天数=最晚时间-最早时间+1
        return StringUtils.isBlank(startDate)
                       || StringUtils.isBlank(endDate) ? 0 : (double) DateUtils.differentDays(
                DateUtils.strToDate(startDate, format),
                DateUtils.strToDate(endDate, format)) + 1;
    }

    private static double getDurationByDate(Date startDate, Date endDate) {
        //总天数=最晚时间-最早时间+1
        return startDate == null
                       || endDate == null ? 0 : (double) DateUtils.differentDays(startDate, endDate) + 1;
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date
                .from(Instant.from(localDateTime.atZone(ZoneId.systemDefault())));
    }

    public static double getDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime == null || endTime == null ? 0 : getDurationByDate(
                convertLocalDateTimeToDate(startTime), convertLocalDateTimeToDate(endTime));
    }

    /**
     * 获取昨天的开始时间,精确到秒
     */
    public static String getLastDateStartTime() {
        return getPastDate(-1) + " 00:00:00";
    }

    /**
     * 获取当天的开始时间,精确到秒
     */
    public static String getTodayDateStartTime() {
        return DateUtils.dateToStr(new Date(), DateUtils.format_YYYY_MM_DD) + " 00:00:00";
    }

    /**
     * 获取多少天以前或者以后的日期
     */
    public static Date getNextNDay(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static String strToStr(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        try {
            return dateToStr(strToDate(str, format_YYYY_MM_DD), format_YYYY_MM_DD);
        } catch (Exception e) {
            log.error("转换失败:{}, e{}", str, e);
            return str;
        }
    }
}