package com.nivesh.production.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gt9 on 6/12/17.
 */

public class DateUtils {
    /*https://stackoverflow.com/questions/454315/how-do-you-format-date-and-time-in-android*/

    public static String getCurrentDate() {
        Date d = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(d);
        return dateStr;
    }

    public static String getCurrentDateStamp() {
        Date d = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(d);
        long output = d.getTime() / 1000L;
        String str = Long.toString(output);
        long timestamp = Long.parseLong(str) * 1000;
//        showLog("timestamp ", String.valueOf(d.getTime()), "with *** Time **", str);
//        return dateStr;
        return String.valueOf(d.getTime());
    }

    public static String getCurrentDateForDisplay() {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String dateStr = simpleDateFormat.format(d);
        return dateStr;
    }

    public static String getCurrentTimeForDisplay() {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr = simpleDateFormat.format(d);
        return dateStr;
    }


    public static String getCurrentDateTextForDisplay() {
        /*https://alvinalexander.com/java/jwarehouse/android/core/java/android/text/format/DateFormat.java.shtml*/
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMM dd");
        String dateStr = simpleDateFormat.format(d);
        return dateStr;
    }


    public static String getCurrentTime() {
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
        String dateStr = simpleDateFormat.format(d);
        return dateStr;
    }

    public static String convertSqlDateToDisplayDate(String inputDate) {
        String resultDate = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = simpleDateFormat.parse(inputDate);
            resultDate = setCurrentDateForDisplay(d) + " at " + setCurrentTimeForDisplay(d);
        } catch (Exception e) {

        }
        return resultDate;
    }

    public static String getCurrentDateTimeForDisplay() {
        String resultStr = getCurrentDateForDisplay() + " at " + getCurrentTimeForDisplay();
        return resultStr;
    }

    public static String setCurrentDateForDisplay(Date d) {
        String resultStr = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            resultStr = simpleDateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    public static String setCurrentTimeForDisplay(Date d) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr = simpleDateFormat.format(d);
        return dateStr;
    }


}
