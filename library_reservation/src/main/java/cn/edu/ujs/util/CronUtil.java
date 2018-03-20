package cn.edu.ujs.util;

/**
 * Created by DELL on 2017/12/29.
 */
public class CronUtil {

    public static String getCron(int second, int minute, int hour, int day, int month, int year) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(second)
                    .append(" ")
                    .append(minute)
                    .append(" ")
                    .append(hour)
                    .append(" ")
                    .append(day)
                    .append(" ")
                    .append(month)
                    .append(" ")
                    .append("?");
        String cron = stringBuffer.toString();
        return cron;
    }
}
