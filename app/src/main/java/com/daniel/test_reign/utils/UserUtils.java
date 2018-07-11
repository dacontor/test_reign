package com.daniel.test_reign.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtils {

    @SuppressLint("SimpleDateFormat")
    public static String convertTimeInMillis(String dateTimeFormat) {

        //"2018-07-11T17:49:53.000Z"
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date mDate = format.parse(dateTimeFormat);
            long m = (mDate.getTime() / 60) % 60;
            long h = (mDate.getTime() / (60 * 60)) % 24;
            return String.valueOf(h);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
