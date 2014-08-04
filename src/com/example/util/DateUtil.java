package com.example.util;

import java.util.Date;

public class DateUtil {
    @SuppressWarnings("deprecation")
    public static String getOutlineListInfo(String date){
        Date localdate = new Date();
        Date comparedate = new Date(date);
        if(comparedate.getYear() != localdate.getYear())
            return comparedate.getYear()+"年";
        if(comparedate.getMonth()!=localdate.getMonth())
            return comparedate.getMonth()+"月"+comparedate.getDay()+"日";
        if(localdate.getDay() - comparedate.getDay() > 1){
            return comparedate.getMonth()+"月"+comparedate.getDay()+"日";
        }
        if(localdate.getDay() - comparedate.getDay() == 1)
            return "昨天";
        if(localdate.getDay() - comparedate.getDay() == 0)
            return comparedate.getHours()+":"+comparedate.getMinutes();
        return comparedate.getYear()+"/"+comparedate.getMonth()+"/"+comparedate.getMinutes();
    }
}
