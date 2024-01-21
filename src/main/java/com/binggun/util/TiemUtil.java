package com.binggun.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TiemUtil {
    //获取当前时间戳 毫秒
    public static Long date() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return date.getTime();
    }
    //获取当前时间
    public static String getCurrentTime() throws Exception {
        return stampToTimeMillisecond(date());
    }

    //时间赚时间戳 毫秒
    public static Long dateToStamp(String s) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = simpleDateFormat.parse(s);
        long time = date.getTime();
        return time;
    }
    //时间戳转时间 秒
    public static String stampToTimeSeconds(long lt) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(lt * 1000L);
        String res = simpleDateFormat.format(date);
        return res;
    }
    //时间戳转时间 毫秒
    public static String stampToTimeMillisecond(long lt) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(lt);
        String res = simpleDateFormat.format(date);
        return res;
    }
    //对比时间 时间1是否大于时间2
    public static boolean ISTimeLimit(Long pd, Long dq, boolean t) throws ParseException {
        boolean b = pd >= dq;
        if (t) {
            return b;
        } else {
            return !b;
        }
    }
    //多种时间格式 赚时间戳
    public static Long getDateTiemString(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        if (s.contains("月")) {
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        } else if (s.contains("-")) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if (s.contains("/")) {
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        } else if (s.contains(".")) {
            simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        }

        Date date = simpleDateFormat.parse(s);
        long time = date.getTime();
        return time;
    }
}
