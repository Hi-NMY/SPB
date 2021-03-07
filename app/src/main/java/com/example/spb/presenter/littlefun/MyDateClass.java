package com.example.spb.presenter.littlefun;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateClass {

    private static Date Aries_star;
    private static Date Taurus_star;
    private static Date Gemini_star;
    private static Date Cancer_star;
    private static Date Leo_star;
    private static Date Virgo_star;
    private static Date Libra_star;
    private static Date Scorpio_star;
    private static Date Sagittarius_star;
    private static Date Capricorn_star;
    private static Date Aquarius_star;
    private static Date Pisces_star;
    private static Date Aries_end;
    private static Date Taurus_end;
    private static Date Gemini_end;
    private static Date Cancer_end;
    private static Date Leo_end;
    private static Date Virgo_end;
    private static Date Libra_end;
    private static Date Scorpio_end;
    private static Date Sagittarius_end;
    private static Date Capricorn_end;
    private static Date Aquarius_end;
    private static Date Pisces_end;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        try {
            Aries_star = simpleDateFormat.parse("03-21");
            Taurus_star = simpleDateFormat.parse("04-21");
            Gemini_star = simpleDateFormat.parse("05-22");
            Cancer_star = simpleDateFormat.parse("06-22");
            Leo_star = simpleDateFormat.parse("07-23");
            Virgo_star = simpleDateFormat.parse("08-24");
            Libra_star = simpleDateFormat.parse("09-24");
            Scorpio_star = simpleDateFormat.parse("10-24");
            Sagittarius_star = simpleDateFormat.parse("11-23");
            Capricorn_star = simpleDateFormat.parse("12-22");
            Aquarius_star = simpleDateFormat.parse("01-21");
            Pisces_star = simpleDateFormat.parse("02-20");
            Aries_end = simpleDateFormat.parse("04-20");
            Taurus_end = simpleDateFormat.parse("05-21");
            Gemini_end = simpleDateFormat.parse("06-21");
            Cancer_end = simpleDateFormat.parse("07-22");
            Leo_end = simpleDateFormat.parse("08-23");
            Virgo_end = simpleDateFormat.parse("09-23");
            Libra_end = simpleDateFormat.parse("10-23");
            Scorpio_end = simpleDateFormat.parse("11-22");
            Sagittarius_end = simpleDateFormat.parse("12-21");
            Capricorn_end = simpleDateFormat.parse("01-20");
            Aquarius_end = simpleDateFormat.parse("02-19");
            Pisces_end = simpleDateFormat.parse("03-20");
        }catch (Exception e){

        }
    }


    public static int showYear(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;
    }

    public static int showMonth(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        return month+1;
    }

    public static int showMonthDay(){
        Calendar c = Calendar.getInstance();
        @SuppressLint("WrongConstant") int monthDay = c.get(Calendar.MONDAY);
        return monthDay;
    }

    public static int showMaxNowMonth(){
        Calendar c = Calendar.getInstance();
        int maxMonth = c.getActualMaximum(Calendar.DATE);
        return maxMonth;
    }

    public static String showNowDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = simpleDateFormat.format(System.currentTimeMillis());
        return nowTime;
    }

    public static String showNowDate2(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowTime = simpleDateFormat.format(System.currentTimeMillis());
        return nowTime;
    }

    public static String showYearMonthDay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = simpleDateFormat.format(System.currentTimeMillis());
        return nowTime;
    }

    public static String showYearMonthDayAddOneDay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH,1);
        String nowTime = simpleDateFormat.format(c.getTime());
        return nowTime;
    }

    public static String getStringDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d = simpleDateFormat.format(date);
        return d;
    }

    public static String getStringDateMonth(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        String d = simpleDateFormat.format(date);
        return d;
    }

    public static String showWeekTable(String date, int f){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(date));
            String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
            if (f == 0){
                if ("1".equals(mWay)) {
                    mWay = "天";
                } else if ("2".equals(mWay)) {
                    mWay = "一";
                } else if ("3".equals(mWay)) {
                    mWay = "二";
                } else if ("4".equals(mWay)) {
                    mWay = "三";
                } else if ("5".equals(mWay)) {
                    mWay = "四";
                } else if ("6".equals(mWay)) {
                    mWay = "五";
                } else if ("7".equals(mWay)) {
                    mWay = "六";
                }
            }
            return mWay;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String showDateClass(String thisDate){
        Timestamp timestamp = new Timestamp(stringToDate(thisDate).getTime());
        Date now = new Date();
        long times = now.getTime() - timestamp.getTime();
        long month = times/((24 * 60 * 60 * 1000) * 20);
        long day = times/(24 * 60 * 60 * 1000);
        long hour = (times/(60 * 60 * 1000) - day * 24);
        long min = ((times/(60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (times/1000-day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuffer stringBuffer = new StringBuffer();
        if (month>0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stringBuffer.append(simpleDateFormat.format(timestamp));
        }else if (day > 0){
            if (day == 1){
                stringBuffer.append("1天前");
            }else if (day == 2){
                stringBuffer.append("2天前");
            }else if (day == 3){
                stringBuffer.append("3天前");
            }else {
                stringBuffer.append(day+"天前");
            }
        }else if (hour>0){
            stringBuffer.append(hour+"小时前");
        }else if (min>0){
            stringBuffer.append(min+"分钟前");
        }else {
            stringBuffer.append("刚刚");
        }
        String aTime = stringBuffer.toString();
        return aTime;
    }

    public static Date stringToDate(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String sendMath(int i){
        float a = (float) i / 1000;
        String num = null;
        if (a > 1){
            String result = String.format("%.1f",a);
            if (result.endsWith("0")){
                num = String.valueOf(Integer.valueOf(result)) + "k";
            }else {
                num = result + "k";
            }
        }else {
            num = String.valueOf(i);
        }
        return num;
    }

    public static String getConstellation(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        try {
            Date birthdate = simpleDateFormat.parse(date);
            if (birthdate.getTime() >= Aries_star.getTime() && birthdate.getTime() <= Aries_end.getTime()){
                return "白羊座";
            }else if (birthdate.getTime() >= Taurus_star.getTime() && birthdate.getTime() <= Taurus_end.getTime()){
                return "金牛座";
            }else if (birthdate.getTime() >= Gemini_star.getTime() && birthdate.getTime() <= Gemini_end.getTime()){
                return "双子座";
            }else if (birthdate.getTime() >= Cancer_star.getTime() && birthdate.getTime() <= Cancer_end.getTime()){
                return "巨蟹座";
            }else if (birthdate.getTime() >= Leo_star.getTime() && birthdate.getTime() <= Leo_end.getTime()){
                return "狮子座";
            }else if (birthdate.getTime() >= Virgo_star.getTime() && birthdate.getTime() <= Virgo_end.getTime()){
                return "处女座";
            }else if (birthdate.getTime() >= Libra_star.getTime() && birthdate.getTime() <= Libra_end.getTime()){
                return "天秤座";
            }else if (birthdate.getTime() >= Scorpio_star.getTime() && birthdate.getTime() <= Scorpio_end.getTime()){
                return "天蝎座";
            }else if (birthdate.getTime() >= Sagittarius_star.getTime() && birthdate.getTime() <= Sagittarius_end.getTime()){
                return "射手座";
            }else if (birthdate.getTime() >= Capricorn_star.getTime() || birthdate.getTime() <= Capricorn_end.getTime()){
                return "摩羯座";
            }else if (birthdate.getTime() >= Aquarius_star.getTime() && birthdate.getTime() <= Aquarius_end.getTime()){
                return "水瓶座";
            }else if (birthdate.getTime() >= Pisces_star.getTime() && birthdate.getTime() <= Pisces_end.getTime()){
                return "双鱼座";
            }
        } catch (ParseException e) {
            return "";
        }
        return "";
    }
}
