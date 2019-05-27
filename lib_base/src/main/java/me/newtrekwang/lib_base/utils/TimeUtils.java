package me.newtrekwang.lib_base.utils;


import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import me.newtrekwang.lib_base.common.TimeConstants;


/**
 * @author newtrekWang
 * @fileName TimeUtils
 * @createDate 2018/10/27 9:20
 * @email 408030208@qq.com
 * @desc 日期时间工具
 */
public final class TimeUtils {
    private TimeUtils(){
        throw new UnsupportedOperationException("u can't touch me !");
    }

    /**
     * 英文简写（默认）日期格式，如：12-01
     */
    public static final String FORMAT_MONTH_DAY = "MM-dd";
    /**
     * 英文简写（默认）日期格式，如：2010-12-01
     */
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    /**
     * 英文简写（默认）时间格式，如：23:14:01
     */
    public static final String FORMAT_TIME = "HH:mm:ss";
    /**
     * 英文简写（默认）时间格式(12小时制)，如：10:14:01
     */
    public static final String FORMAT_TIME_12HOUR = "hh:mm:ss";
    /**
     * 英文简写（默认）全时间格式，如：2010-12-01 23:14:05
     */
    public static final String FORMAT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 英文简写（默认）全时间格式(12小时制)，如：2010-12-01 10:14:05
     */
    public static final String FORMAT_DATE_PATTERN_12HOUR = "yyyy-MM-dd hh:mm:ss";
    /**
     * 中文简写日期格式，如：12月01日
     */
    public static final String FORMAT_CN_MONTH_DAY = "MM月dd日";
    /**
     * 中文简写日期格式，如：2010年12月01日
     */
    public static final String FORMAT_CN_YEAR_MONTH_DAY = "yyyy年MM月dd日";
    /**
     * 中文简写（默认）时间格式，如：23:14:01
     */
    public static final String FORMAT_CN_TIME = "HH时mm分ss秒";
    /**
     * 中文简写（默认）时间格式(12小时制)，如：10:14:01
     */
    public static final String FORMAT_CN_TIME_12HOUR = "hh时mm分ss秒";
    /**
     * 中文简写全时间格式，如：2010年12月01日 23时14分05秒
     */
    public static final String FORMAT_CN_DATE_PATTERN = "yyyy年MM月dd日 HH时mm分ss秒";
    /**
     * 中文简写全时间格式，如：2010年12月01日 10时14分05秒
     */
    public static final String FORMAT_CN_DATE_PATTERN_12HOUR = "yyyy年MM月dd日 hh时mm分ss秒";

    /**
     * 使每个线程获取的SimpleDateFormat不是同一个对象
     */
    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取默认的SimpleDateFormat
     * @return 默认的SimpleDateFormat ，时间格式为yyyy-MM-dd HH:mm:ss
     */
    private static SimpleDateFormat getDefaultFormat() {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_PATTERN, Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * 时间戳（毫秒）转为时间字符串，格式为yyyy-MM-dd HH:mm:ss
     *
     * @param millis 时间戳（毫秒）
     * @return 时间字符串，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, getDefaultFormat());
    }

    /**
     * 时间戳（毫秒）转为时间字符串
     *
     * @param millis 时间戳（毫秒）
     * @param format dateFormat
     * @return 时间字符串
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 时间字符串转时间戳（毫秒），注意：时间格式应为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串，注意：格式应为yyyy-MM-dd HH:mm:ss
     * @return 时间戳（毫秒）
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, getDefaultFormat());
    }

    /**
     *  时间字符串转时间戳（毫秒）
     *
     * @param time   时间字符串，格式与format相互对应
     * @param format 自定义的DateFormat
     * @return 时间戳（毫秒）
     */
    public static long string2Millis(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 时间字符串转为Date对象
     *
     * @param time 时间字符串，注意：格式应为yyyy-MM-dd HH:mm:ss
     * @return date对象
     */
    public static Date string2Date(final String time) {
        return string2Date(time, getDefaultFormat());
    }

    /**
     * 时间字符串转为Date对象
     *
     * @param time   时间字符串，与format对应
     * @param format 自定义的format
     * @return date对象
     */
    public static Date string2Date(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * date转时间字符串
     *
     * @param date date对象
     * @return 时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static String date2String(final Date date) {
        return date2String(date, getDefaultFormat());
    }

    /**
     *  date转时间字符串
     *
     * @param date   date对象
     * @param format 自定义的dateFormat
     * @return 时间字符串，格式为format设置的格式
     */
    public static String date2String(final Date date, @NonNull final DateFormat format) {
        return format.format(date);
    }

    /**
     * date转时间戳（毫秒）
     *
     * @param date 要转的date
     * @return 时间戳（毫秒）
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * 时间戳（毫秒）转date
     *
     * @param millis 时间戳（毫秒）
     * @return date
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * Return the time span, in unit.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time1 The first formatted time string.
     * @param time2 The second formatted time string.
     * @param unit  The unit of time span.
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}</li>
     *              <li>{@link TimeConstants#SEC }</li>
     *              <li>{@link TimeConstants#MIN }</li>
     *              <li>{@link TimeConstants#HOUR}</li>
     *              <li>{@link TimeConstants#DAY }</li>
     *              </ul>
     * @return the time span, in unit
     */
    public static long getTimeSpan(final String time1,
                                   final String time2,
                                   @TimeConstants.Unit final int unit) {
        return getTimeSpan(time1, time2, getDefaultFormat(), unit);
    }

    /**
     * 返回两时间之间的跨度，可选跨度时间单位符号
     *
     * @param time1  第一个时间字符串
     * @param time2  第二个时间字符串
     * @param format 时间格式对象DateFormat
     * @param unit   时间跨度单位
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return 两时间之间的跨度，单位为设置的时间单位
     */
    public static long getTimeSpan(final String time1,
                                   final String time2,
                                   @NonNull final DateFormat format,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(string2Millis(time1, format) - string2Millis(time2, format), unit);
    }

    /**
     * 返回两时间之间的跨度，可选跨度时间单位符号
     *
     * @param date1 第一个时间date
     * @param date2 第二个时间date
     * @param unit  时间跨度单位
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}</li>
     *              <li>{@link TimeConstants#SEC }</li>
     *              <li>{@link TimeConstants#MIN }</li>
     *              <li>{@link TimeConstants#HOUR}</li>
     *              <li>{@link TimeConstants#DAY }</li>
     *              </ul>
     * @return 两时间之间的跨度，单位为设置的时间单位
     */
    public static long getTimeSpan(final Date date1,
                                   final Date date2,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(date2Millis(date1) - date2Millis(date2), unit);
    }

    /**
     * 返回两时间之间的跨度，可选跨度时间单位符号
     *
     * @param millis1 第一个时间的时间戳
     * @param millis2 第二个时间的时间戳
     * @param unit    时间跨度单位
     *                <ul>
     *                <li>{@link TimeConstants#MSEC}</li>
     *                <li>{@link TimeConstants#SEC }</li>
     *                <li>{@link TimeConstants#MIN }</li>
     *                <li>{@link TimeConstants#HOUR}</li>
     *                <li>{@link TimeConstants#DAY }</li>
     *                </ul>
     * @return 两时间之间的跨度，单位为设置的时间单位
     */
    public static long getTimeSpan(final long millis1,
                                   final long millis2,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(millis1 - millis2, unit);
    }

    /**
     * 返回两时间之间的跨度，字符串描述，可选精确度
     *
     * @param time1     第一个时间字符串，注意：应为yyyy-MM-dd HH:mm:ss
     * @param time2     第二个时间字符串，注意：应为yyyy-MM-dd HH:mm:ss
     * @param precision 描述精确度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return  跨度时间描述字符串
     */
    public static String getFitTimeSpan(final String time1,
                                        final String time2,
                                        final int precision) {
        long delta = string2Millis(time1, getDefaultFormat()) - string2Millis(time2, getDefaultFormat());
        return millis2FitTimeSpan(delta, precision);
    }

    /**
     * 返回两时间之间的跨度，字符串描述，可选精确度
     *
     * @param time1     第一个时间字符串
     * @param time2     第二个时间字符串
     * @param format    时间字符串的格式
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return 跨度时间描述字符串
     */
    public static String getFitTimeSpan(final String time1,
                                        final String time2,
                                        @NonNull final DateFormat format,
                                        final int precision) {
        long delta = string2Millis(time1, format) - string2Millis(time2, format);
        return millis2FitTimeSpan(delta, precision);
    }

    /**
     * 返回两时间之间的跨度，字符串描述，可选精确度
     *
     * @param date1     第一个date.
     * @param date2     第二个date.
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return 跨度时间描述字符串
     */
    public static String getFitTimeSpan(final Date date1, final Date date2, final int precision) {
        return millis2FitTimeSpan(date2Millis(date1) - date2Millis(date2), precision);
    }

    /**
     * 返回两时间之间的跨度，字符串描述，可选精确度
     *
     * @param millis1   第一个时间戳（毫秒）
     * @param millis2   第二个时间戳（毫秒）
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return 跨度时间描述字符串
     */
    public static String getFitTimeSpan(final long millis1,
                                        final long millis2,
                                        final int precision) {
        return millis2FitTimeSpan(millis1 - millis2, precision);
    }

    /**
     * 返回当前的时间戳（毫秒）
     *
     * @return 当前的时间戳（毫秒）
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 返回当前的时间字符串，默认格式yyyy-MM-dd HH:mm:ss
     *
     * @return 当前的时间字符串，默认格式yyyy-MM-dd HH:mm:ss
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), getDefaultFormat());
    }

    /**
     * 返回当前的时间字符串
     *
     * @param format 时间字符串格式对象
     * @return 当前的时间字符串
     */
    public static String getNowString(@NonNull final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 返回当前的Date
     *
     * @return 当前的Date
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 返回指定时间距离当前时间的间隔，可选时间单位
     *
     * @param time 指定的时间字符串，注意：格式应为：yyyy-MM-dd HH:mm:ss
     * @param unit 间隔时间单位
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}</li>
     *             <li>{@link TimeConstants#SEC }</li>
     *             <li>{@link TimeConstants#MIN }</li>
     *             <li>{@link TimeConstants#HOUR}</li>
     *             <li>{@link TimeConstants#DAY }</li>
     *             </ul>
     * @return 指定时间距离当前时间的间隔
     */
    public static long getTimeSpanByNow(final String time, @TimeConstants.Unit final int unit) {
        return getTimeSpan(time, getNowString(), getDefaultFormat(), unit);
    }

    /**
     * 返回指定时间距离当前时间的间隔，可选时间单位
     *
     * @param time   指定的时间字符串
     * @param format 时间格式
     * @param unit   间隔时间单位
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return 指定时间距离当前时间的间隔
     */
    public static long getTimeSpanByNow(final String time,
                                        @NonNull final DateFormat format,
                                        @TimeConstants.Unit final int unit) {
        return getTimeSpan(time, getNowString(format), format, unit);
    }

    /**
     * 返回指定时间距离当前时间的间隔，可选时间单位
     *
     * @param date 指定时间的date
     * @param unit 间隔时间单位
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}</li>
     *             <li>{@link TimeConstants#SEC }</li>
     *             <li>{@link TimeConstants#MIN }</li>
     *             <li>{@link TimeConstants#HOUR}</li>
     *             <li>{@link TimeConstants#DAY }</li>
     *             </ul>
     * @return 指定时间距离当前时间的间隔
     */
    public static long getTimeSpanByNow(final Date date, @TimeConstants.Unit final int unit) {
        return getTimeSpan(date, new Date(), unit);
    }

    /**
     * 返回指定时间距离当前时间的间隔，可选时间单位
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @param unit   间隔时间单位
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return 指定时间距离当前时间的间隔
     */
    public static long getTimeSpanByNow(final long millis, @TimeConstants.Unit final int unit) {
        return getTimeSpan(millis, System.currentTimeMillis(), unit);
    }

    /**
     * 返回指定时间距离当前时间的间隔字符串描述，可选描述精度
     *
     * @param time      指定时间的字符串，注意格式应为：yyyy-MM-dd HH:mm:ss
     * @param precision 描述精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 返回指定时间距离当前时间的间隔字符串描述
     */
    public static String getFitTimeSpanByNow(final String time, final int precision) {
        return getFitTimeSpan(time, getNowString(), getDefaultFormat(), precision);
    }

    /**
     * 返回指定时间距离当前时间的间隔字符串描述，可选描述精度
     *
     * @param time      指定时间的字符串
     * @param format    时间格式
     * @param precision 描述精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 指定时间距离当前时间的间隔字符串描述
     */
    public static String getFitTimeSpanByNow(final String time,
                                             @NonNull final DateFormat format,
                                             final int precision) {
        return getFitTimeSpan(time, getNowString(format), format, precision);
    }

    /**
     * 返回指定时间距离当前时间的间隔字符串描述，可选描述精度
     *
     * @param date      指定时间的date
     * @param precision 描述精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 指定时间距离当前时间的间隔字符串描述
     */
    public static String getFitTimeSpanByNow(final Date date, final int precision) {
        return getFitTimeSpan(date, getNowDate(), precision);
    }

    /**
     * 返回指定时间距离当前时间的间隔字符串描述，可选描述精度
     *
     * @param millis    指定时间的时间戳（毫秒）
     * @param precision 描述精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 指定时间距离当前时间的间隔字符串描述
     */
    public static String getFitTimeSpanByNow(final long millis, final int precision) {
        return getFitTimeSpan(millis, System.currentTimeMillis(), precision);
    }

    /**
     * 返回指定时间距离当前时间的友好描述
     *
     * @param time 指定时间，注意格式应为： yyyy-MM-dd HH:mm:ss
     * @return 指定时间距离当前时间的友好描述
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time) {
        return getFriendlyTimeSpanByNow(time, getDefaultFormat());
    }

    /**
     * 返回指定时间距离当前时间的友好描述
     *
     * @param time   指定时间
     * @param format 时间格式
     * @return 指定时间距离当前时间的友好描述
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time,
                                                  @NonNull final DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    /**
     * 返回指定时间距离当前时间的友好描述
     *
     * @param date 指定时间的date
     * @return 指定时间距离当前时间的友好描述
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * 返回指定时间距离当前时间的友好描述
     *
     * @param millis 指定时间的时间戳
     * @return 指定时间距离当前时间的友好描述
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) {
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            return String.format("%tc", millis);
          }
        if (span < 1000) {
            return "刚刚";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     *  获取当天00:00的时间戳
     * @return 当天00:00的时间戳
     */
    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获取指定时间经过时间间隔后的时间戳（毫秒）
     *
     * @param millis   指定的时间，时间戳（毫秒）
     * @param timeSpan 间隔时间
     * @param unit    间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间戳（毫秒）
     */
    public static long getMillis(final long millis,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间戳（毫秒）
     *
     * @param time     指定的时间字符串，注意：格式应为yyyy-MM-dd HH:mm:ss
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间戳（毫秒）
     */
    public static long getMillis(final String time,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return getMillis(time, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间戳（毫秒）
     *
     * @param time     指定的时间字符串
     * @param format   指定的时间字符串时间格式
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间戳（毫秒）
     */
    public static long getMillis(final String time,
                                 @NonNull final DateFormat format,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return string2Millis(time, format) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间戳（毫秒）
     *
     * @param date     指定的时间date
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间戳（毫秒）
     */
    public static long getMillis(final Date date,
                                 final long timeSpan,
                                 @TimeConstants.Unit final int unit) {
        return date2Millis(date) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param millis   指定时间的时间戳（毫秒）
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间字符串
     */
    public static String getString(final long millis,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return getString(millis, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间字符串
     *
     * @param millis   指定时间的时间戳（毫秒）
     * @param format   返回的时间字符串格式
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间字符串
     */
    public static String getString(final long millis,
                                   @NonNull final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 获取指定时间经过时间间隔后的时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param time     指定时间的时间，格式应为yyyy-MM-dd HH:mm:ss
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间字符串
     */
    public static String getString(final String time,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return getString(time, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param time     指定时间的时间
     * @param format   时间格式
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间字符串
     */
    public static String getString(final String time,
                                   @NonNull final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 获取指定时间经过时间间隔后的时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param date     指定时间的date
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间字符串
     */
    public static String getString(final Date date,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return getString(date, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间字符串
     *
     * @param date     指定时间的date
     * @param format   时间格式
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间字符串
     */
    public static String getString(final Date date,
                                   @NonNull final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants.Unit final int unit) {
        return millis2String(date2Millis(date) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 获取指定时间经过时间间隔后的时间date
     *
     * @param millis   指定时间的时间戳（毫秒）
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间date
     */
    public static Date getDate(final long millis,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 获取指定时间经过时间间隔后的时间date
     *
     * @param time     指定的时间，注意格式应为：yyyy-MM-dd HH:mm:ss
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间date
     */
    public static Date getDate(final String time,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return getDate(time, getDefaultFormat(), timeSpan, unit);
    }

    /**
     * 获取指定时间经过时间间隔后的时间date
     *
     * @param time     指定的时间
     * @param format   时间格式
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间date
     */
    public static Date getDate(final String time,
                               @NonNull final DateFormat format,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 获取指定时间经过时间间隔后的时间date
     *
     * @param date     指定的时间的date
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 指定时间经过时间间隔后的时间date
     */
    public static Date getDate(final Date date,
                               final long timeSpan,
                               @TimeConstants.Unit final int unit) {
        return millis2Date(date2Millis(date) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 获取当前时间经过指定时间间隔后的时间戳
     *
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 当前时间经过指定时间间隔后的时间戳
     */
    public static long getMillisByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    /**
     * 获取当前时间经过指定时间间隔后的时间字符串描述,格式为yyyy-MM-dd HH:mm:ss
     *
     * @param timeSpan 间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 当前时间经过指定时间间隔后的时间字符串描述
     */
    public static String getStringByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getStringByNow(timeSpan, getDefaultFormat(), unit);
    }

    /**
     * 获取当前时间经过指定时间间隔后的时间字符串描述
     *
     * @param timeSpan 间隔时间
     * @param format   时间格式
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 当前时间经过指定时间间隔后的时间字符串描述
     */
    public static String getStringByNow(final long timeSpan,
                                        @NonNull final DateFormat format,
                                        @TimeConstants.Unit final int unit) {
        return getString(getNowMills(), format, timeSpan, unit);
    }

    /**
     * 获取当前时间经过指定时间间隔后的时间date
     *
     * @param timeSpan T间隔时间
     * @param unit     间隔时间单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return 当前时间经过指定时间间隔后的时间date
     */
    public static Date getDateByNow(final long timeSpan, @TimeConstants.Unit final int unit) {
        return getDate(getNowMills(), timeSpan, unit);
    }

    /**
     * 返回指定时间是不是今天，指定时间格式应为yyyy-MM-dd HH:mm:ss
     *
     * @param time 指定时间，时间格式应为yyyy-MM-dd HH:mm:ss
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, getDefaultFormat()));
    }

    /**
     * 返回指定时间是不是今天
     *
     * @param time   指定时间
     * @param format 指定时间的时间格式
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time, @NonNull final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * 返回指定时间是不是今天
     *
     * @param date 指定时间的date
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * 返回指定时间是不是今天
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    /**
     * 返回指定时间是不是闰年
     *
     * @param time 指定时间，时间格式应为：yyyy-MM-dd HH:mm:ss
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time) {
        return isLeapYear(string2Date(time, getDefaultFormat()));
    }

    /**
     * 返回指定时间是不是闰年
     *
     * @param time   指定时间
     * @param format 指定时间的时间格式
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time, @NonNull final DateFormat format) {
        return isLeapYear(string2Date(time, format));
    }

    /**
     * 返回指定时间是不是闰年
     *
     * @param date 指定时间的date
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 返回指定时间是不是闰年
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * 返回指定时间是不是闰年
     *
     * @param year 年
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 返回指定时间是中文的星期几
     *
     * @param time 指定时间，格式应为yyyy-MM-dd HH:mm:ss
     * @return 指定时间是中文的星期几
     */
    public static String getChineseWeek(final String time) {
        return getChineseWeek(string2Date(time, getDefaultFormat()));
    }

    /**
     * 返回指定时间是中文的星期几
     *
     * @param time   指定时间
     * @param format 指定时间的时间格式
     * @return 指定时间是中文的星期几
     */
    public static String getChineseWeek(final String time, @NonNull final DateFormat format) {
        return getChineseWeek(string2Date(time, format));
    }

    /**
     * 返回指定时间是中文的星期几
     *
     * @param date 指定时间的date
     * @return 指定时间是中文的星期几
     */
    public static String getChineseWeek(final Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 返回指定时间是中文的星期几
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @return 指定时间是中文的星期几
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 返回指定时间是英文的星期几
     *
     * @param time 指定时间，格式应为yyyy-MM-dd HH:mm:ss
     * @return 返回指定时间是英文的星期几
     */
    public static String getUSWeek(final String time) {
        return getUSWeek(string2Date(time, getDefaultFormat()));
    }

    /**
     * 返回指定时间是英文的星期几
     *
     * @param time   指定时间
     * @param format 指定时间的时间格式
     * @return 指定时间是英文的星期几
     */
    public static String getUSWeek(final String time, @NonNull final DateFormat format) {
        return getUSWeek(string2Date(time, format));
    }

    /**
     * 返回指定时间是英文的星期几
     *
     * @param date 指定时间的date
     * @return 指定时间是英文的星期几
     */
    public static String getUSWeek(final Date date) {
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    /**
     * 返回指定时间是英文的星期几
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @return 指定时间是英文的星期几
     */
    public static String getUSWeek(final long millis) {
        return getUSWeek(new Date(millis));
    }

    /**
     * 返回指定时间的指定日历单位域上的值是多少
     *
     * @param time  指定时间，格式应为yyyy-MM-dd HH:mm:ss
     * @param field 日历单位域
     *              <ul>
     *              <li>{@link Calendar#ERA}</li>
     *              <li>{@link Calendar#YEAR}</li>
     *              <li>{@link Calendar#MONTH}</li>
     *              <li>...</li>
     *              <li>{@link Calendar#DST_OFFSET}</li>
     *              </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final String time, final int field) {
        return getValueByCalendarField(string2Date(time, getDefaultFormat()), field);
    }

    /**
     * 返回指定时间的指定日历单位域上的值是多少
     *
     * @param time   指定时间
     * @param format 指定时间的时间格式
     * @param field  日历单位域
     *               <ul>
     *               <li>{@link Calendar#ERA}</li>
     *               <li>{@link Calendar#YEAR}</li>
     *               <li>{@link Calendar#MONTH}</li>
     *               <li>...</li>
     *               <li>{@link Calendar#DST_OFFSET}</li>
     *               </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final String time,
                                              @NonNull final DateFormat format,
                                              final int field) {
        return getValueByCalendarField(string2Date(time, format), field);
    }

    /**
     * 返回指定时间的指定日历单位域上的值是多少
     *
     * @param date  指定时间的date
     * @param field 日历单位域
     *              <ul>
     *              <li>{@link Calendar#ERA}</li>
     *              <li>{@link Calendar#YEAR}</li>
     *              <li>{@link Calendar#MONTH}</li>
     *              <li>...</li>
     *              <li>{@link Calendar#DST_OFFSET}</li>
     *              </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final Date date, final int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * 返回指定时间的指定日历单位域上的值是多少
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @param field  日历单位域
     *               <ul>
     *               <li>{@link Calendar#ERA}</li>
     *               <li>{@link Calendar#YEAR}</li>
     *               <li>{@link Calendar#MONTH}</li>
     *               <li>...</li>
     *               <li>{@link Calendar#DST_OFFSET}</li>
     *               </ul>
     * @return the value of the given calendar field
     */
    public static int getValueByCalendarField(final long millis, final int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(field);
    }

    /**
     * 12生肖常量
     */
    private static final String[] CHINESE_ZODIAC =
            {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    /**
     * 返回指定时间所属的中文生肖名
     *
     * @param time 指定时间，格式应为yyyy-MM-dd HH:mm:ss
     * @return 指定时间所属的中文生肖名
     */
    public static String getChineseZodiac(final String time) {
        return getChineseZodiac(string2Date(time, getDefaultFormat()));
    }

    /**
     * 返回指定时间所属的中文生肖名
     *
     * @param time   指定时间
     * @param format 指定时间的时间格式
     * @return 指定时间所属的中文生肖名
     */
    public static String getChineseZodiac(final String time, @NonNull final DateFormat format) {
        return getChineseZodiac(string2Date(time, format));
    }

    /**
     * 返回指定时间所属的中文生肖名
     *
     * @param date 指定时间的date
     * @return 指定时间所属的中文生肖名
     */
    public static String getChineseZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 返回指定时间所属的中文生肖名
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @return 指定时间所属的中文生肖名
     */
    public static String getChineseZodiac(final long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    /**
     * 返回指定时间所属的中文生肖名
     *
     * @param year 年
     * @return 指定时间所属的中文生肖名
     */
    public static String getChineseZodiac(final int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    /**
     * 星座常量
     */
    private static final int[]    ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIAC       = {
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"
    };

    /**
     * 返回指定时间的星座名
     * @param time 指定时间，格式应为yyyy-MM-dd HH:mm:ss
     * @return 指定时间的星座名
     */
    public static String getZodiac(final String time) {
        return getZodiac(string2Date(time, getDefaultFormat()));
    }

    /**
     * 返回指定时间的星座名
     *
     * @param time   指定时间
     * @param format 指定时间的格式
     * @return 指定时间的星座名
     */
    public static String getZodiac(final String time, @NonNull final DateFormat format) {
        return getZodiac(string2Date(time, format));
    }

    /**
     * 返回指定时间的星座名
     *
     * @param date 指定时间的date
     * @return 指定时间的星座名
     */
    public static String getZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * 返回指定时间的星座名
     *
     * @param millis 指定时间的时间戳（毫秒）
     * @return 指定时间的星座名
     */
    public static String getZodiac(final long millis) {
        return getZodiac(millis2Date(millis));
    }

    /**
     * 返回指定时间的星座名
     *
     * @param month 月
     * @param day   天
     * @return 指定时间的星座名
     */
    public static String getZodiac(final int month, final int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    /**
     * 中国科学院国家授时中心
     */
    private static final String NET_TIME_WEB_URL = "http://www.ntsc.ac.cn";

    /**
     * 获取网络时间字符串
     * @return 网络时间字符串，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getNowTimeStringFromNet(){
        return getNowTimeStringFromNet(NET_TIME_WEB_URL,getDefaultFormat());
    }

    /**
     * 获取网络时间字符串
     *
     * @param format 时间字符串格式
     * @return 网络时间字符串
     */
    public static String getNowTimeStringFromNet(DateFormat format){
        return getNowTimeStringFromNet(NET_TIME_WEB_URL,format);
    }
    /**
     * 获取网络时间字符串
     * @param timeWebUrl 网络时间服务器地址
     * @param format 时间格式
     * @return 网络时间字符串
     */
    public static String getNowTimeStringFromNet(String timeWebUrl, DateFormat format){
        return format.format(getNowTimeDateFromNet(timeWebUrl));
    }

    /**
     * 获取网络时间的date
     * @param timeWebUrl  网络时间服务器地址
     * @return 网络时间的date
     */
    public static Date getNowTimeDateFromNet(String timeWebUrl){
        try {
            URL url = new URL(timeWebUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
            return new Date(conn.getDate());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取网络时间的date
     * @return 网络时间的date
     */
    public static Date getNowTimeDateFromNet(){
        return getNowTimeDateFromNet(NET_TIME_WEB_URL);
    }

    /**
     * 获取网络时间的时间戳
     * @return 网络时间的时间戳
     */
    public static long getNowTimeMillisFromNet(){
        return getNowTimeDateFromNet().getTime();
    }

    /**
     * 获取网络时间的时间戳
     * @param timeWebUrl 网络时间服务器地址
     * @return 网络时间的时间戳
     */
    public static long getNowTimeMillisFromNet(String timeWebUrl){
        return getNowTimeDateFromNet(timeWebUrl).getTime();
    }


    private static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    private static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    private static String millis2FitTimeSpan(long millis, int precision) {
        if (precision <= 0) {return null;}
        precision = Math.min(precision, 5);
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        if (millis == 0) {return 0 + units[precision - 1];}
        StringBuilder sb = new StringBuilder();
        if (millis < 0) {
            sb.append("-");
            millis = -millis;
        }
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

}
