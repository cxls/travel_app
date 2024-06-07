package com.hjq.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 * @author flightz-
 */
public class DateUtils {
    public static String getRelativeTime(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTime(date);

        long diffInMillis = now.getTimeInMillis() - target.getTimeInMillis();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
        long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

        if (diffInMinutes < 1) {
            return "刚刚";
        } else if (diffInMinutes < 60) {
            return diffInMinutes + " 分钟前";
        } else if (diffInHours < 24) {
            return diffInHours + " 小时前";
        } else if (diffInDays < 1) {
            return "今天";
        } else if (diffInDays == 1) {
            return "昨天";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.format(date);
        }
    }
}
