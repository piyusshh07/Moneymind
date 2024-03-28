package com.project.moneymind.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class helper {
    public static String formatdate(Date date){
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return dateFormat.format(date);
    }
    public static String format_date_month(Date date){

        SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM,YY");
        return dateFormat.format(date);
    }
}
