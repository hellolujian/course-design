package cn.edu.ujs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DELL on 2017/12/26.
 */
public class TimeUtil {

    /**返回当前时间的字符串格式yyyy-MM-dd*/
    public static String getDateShort() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        return dateString;
    }

    /**String转Date（yyyy-MM-dd)*/
    public static Date stringToDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * String转Date
     * 时间格式（yyyy-MM-dd hh:mm:ss）
     */
    public static Date longStrToDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断当前时间是否在date之前
     * 时间格式yy-MM-dd hh:mm:ss
     */
    public static boolean isDateBefore(String date) {
        try {
            Date date1 = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return date1.before(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isDate1BeforeDate2(String date1, String date2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(date1).before(simpleDateFormat.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断当前时间是否在date之后
     * 时间格式yy-MM-dd hh:mm:ss
     */
    public static boolean isDateAfter(String date) {
        try {
            Date date1 = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return date1.after(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 给定时间加一天操作
     * @param dateStr
     * @return
     */
    public static Date addOneDay(String dateStr) {
        Date date = stringToDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date tomorrow = calendar.getTime();
        return tomorrow;
    }



    public static Date addOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date tomorrow = calendar.getTime();
        return tomorrow;
    }

    public static Date addHour(Date date, Integer hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY,hour);
        Date afterAddTime = calendar.getTime();
        return afterAddTime;
    }

    /**
     * 当前时间加上指定分钟
     * @param minute
     * @return
     */
    public static Date addMinute(Integer minute) {

        long currentTime = System.currentTimeMillis();
        currentTime += minute*60*1000;
        Date date = new Date(currentTime);
        return date;

    }

    public static Date addMinute(Date date,Integer minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,minute);
        Date afterAddTime = calendar.getTime();
        return afterAddTime;
    }

    public static Date addHour(Integer hour) {
        long currentTime = System.currentTimeMillis();
        currentTime += hour*60*60*1000;
        Date date = new Date(currentTime);
        return date;
    }

    /**
     * 当前时间加上指定小时
     * @param second
     * @return
     */
    public static Date addSecond(Integer second) {
        long currentTime = System.currentTimeMillis();
        currentTime += second*1000;
        Date date = new Date(currentTime);
        return date;
    }

    /**
     * Date转String
     * @param date
     * @return
     */
    public static String dateToLongStr(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    public static String dateToShortString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 获取指定日期的时
     * @param date
     * @return
     */
    public static int getHourByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 获取指定日期的分
     * @param date
     * @return
     */
    public static int getMinuteByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    /**
     * 获取指定日期的秒
     * @param date
     * @return
     */
    public static int getSecondByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int second = calendar.get(Calendar.SECOND);
        return second;
    }

    /**
     * 获取指定日期的年
     * @param date
     * @return
     */
    public static int getYearByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取指定日期的月
     * @param date
     * @return
     */
    public static int getMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        return month + 1;
    }

    /**
     * 获取指定日期的日
     * @param date
     * @return
     */
    public static int getDayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 判断两个日期是否相等
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static boolean isEqual(String dateStr1, String dateStr2) {
        Date date1 = longStrToDate(dateStr1);
        Date date2 = longStrToDate(dateStr2);
        boolean flag = date1.equals(date2);
        return flag;
    }

    /**
     * 计算两个日期的天数差
     * @param date1
     * @param date2
     * @return
     */
    public static long countDays(Date date1, Date date2) {
        //long time1 = date1.getTime();
        //long time2 = date2.getTime();
        long difference =  (date1.getTime()-date2.getTime())/86400000;
        return Math.abs(difference);
    }

    /**
     * 计算两个日期的小时差
     * @param date1
     * @param date2
     * @return
     */
    public static long countHours(Date date1, Date date2) {
        long difference = (date1.getTime()-date2.getTime())/(1000*60*60);
        return Math.abs(difference);
    }

}
