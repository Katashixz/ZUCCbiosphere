package com.example.zuccbiosphere.filter;

import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getHoursFilter {
    public static String getTimeDiff(String date) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        }
        try {
            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = mDateFormat.parse(date);
            Date now = new Date();
            // 这样得到的差值是微秒级别
            long diff = now.getTime() - parse.getTime();
            // 只能精确到日 无法具细到年 月 不能确定一个月具体多少天 不能确定一年具体多少天
            // 获取日
            long day = diff / (1000 * 60 * 60 * 24);
            diff = diff % (1000 * 60 * 60 * 24);

            // 获取时
            long hour = diff / (1000 * 60 * 60);
            diff = diff % (1000 * 60 * 60);

            // 获取分
            long min = diff / (1000 * 60);
            diff = diff % (1000 * 60);

            // 获取秒
            long sec = diff / 1000;

            if (sec > 30) {
                min ++;
            }
            if (min > 30) {
                hour ++;
            }
            if (hour > 24) {
                day ++;
            }
            if (hour == 0){
                return min + "分钟前";
            }
            else if (day > 0) {
                return day + "天前";
            }
            else{
                return hour + "小时前";
            }
        } catch (ParseException e) {
            return "";
        }
    }
}
