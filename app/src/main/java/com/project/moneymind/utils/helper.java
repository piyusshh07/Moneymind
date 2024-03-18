package com.project.moneymind.utils;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import java.util.Date;

public class helper {
    public static String formatdate(Date date){
        SimpleDateFormat dateFormat= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return dateFormat.format(date);
        }
        return null;
    }
}
