package com.vege.service.preprocessing.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {

    public static String format(String date_time) {
        SimpleDateFormat originFmt = null;
        if (date_time.length() > 16) {
            originFmt = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        } else {
            originFmt = new SimpleDateFormat("yyyy/M/dd H:mm");
        }
        String result = "";
        try {
            SimpleDateFormat newFmt = new SimpleDateFormat("yyyy/M/dd H:mm");
            Date date = originFmt.parse(date_time);
            result = newFmt.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	/*public static void main(String[] args) {
		String time = "2017/8/30 9:06";
		System.out.println(time.length());
		String result = TimeFormat.format("2017/8/30 9:06");
		System.out.println(result);
	}*/
}
