package com.xupt.ttms.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TypeCasting {
    public static LocalDateTime formatStringToLocalDateTime(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date,dtf);
    }
    //1.LocalDateTime转为"yyyy/MM/dd"
    public static String formatLocalDateTimeString1(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dtf);
    }

    //2.LocalDateTime转为"yyyy-MM-dd HH:mm"
    public static String formatLocalDateTimeString(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(dtf);
    }

    //3.将"yyyy-MM-dd HH:mm:ss"转化为"yyyy-MM-dd HH:mm"
    public static String formatStringToString(String date){
        String result = "";
        char[] chars = date.toCharArray();
        for (int i = 0; i < chars.length-3; i++) {
            result += chars[i];
        }
        return result;
    }
}