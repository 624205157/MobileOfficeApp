package com.fish.timeline;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by miao on 2018/10/10.
 */
public class DateUtil {
    public static long parseDate(String patten, String date) {
        DateFormat d = new SimpleDateFormat(patten, Locale.CHINA);
        try {
            return d.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String format(String patten, long date) {
        DateFormat d = new SimpleDateFormat(patten, Locale.CHINA);
        return d.format(new Date(date));
    }
}
