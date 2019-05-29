package com.ley.innovation.contest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author ley
 **/
public class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //将日期格式的字符串转换为长整型
    public static long date2Long(String date) throws ParseException {
        return dateFormat.parse(date).getTime();
    }
}
