package com.example.util;

import java.util.Date;

public class DateUtil {
    @SuppressWarnings("deprecation")
    public static String getOutlineListInfo(String date){
        Date localdate = new Date();
        Date comparedate = new Date(date);
        if(comparedate.getYear() != localdate.getYear())
            return comparedate.getYear()+"��";
        if(comparedate.getMonth()!=localdate.getMonth())
            return comparedate.getMonth()+"��"+comparedate.getDay()+"��";
        if(localdate.getDay() - comparedate.getDay() > 1){
            return comparedate.getMonth()+"��"+comparedate.getDay()+"��";
        }
        if(localdate.getDay() - comparedate.getDay() == 1)
            return "����";
        if(localdate.getDay() - comparedate.getDay() == 0)
            return comparedate.getHours()+":"+comparedate.getMinutes();
        return comparedate.getYear()+"/"+comparedate.getMonth()+"/"+comparedate.getMinutes();
    }
}
