package com.example.spb.presenter.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateClass {

    private static Date Aries_start;
    private static Date Taurus_start;
    private static Date Gemini_start;
    private static Date Cancer_start;
    private static Date Leo_start;
    private static Date Virgo_start;
    private static Date Libra_start;
    private static Date Scorpio_start;
    private static Date Sagittarius_start;
    private static Date Capricorn_start;
    private static Date Aquarius_start;
    private static Date Pisces_start;
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

    private static Date Morn_start;
    private static Date Morn_end;
    private static Date Morning_start;
    private static Date Morning_end;
    private static Date Afternoon_start;
    private static Date Afternoon_end;
    private static Date Dusk_start;
    private static Date Dusk_end;
    private static Date Night_start;
    private static Date Night_end;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        try {
            Aries_start = simpleDateFormat.parse("03-21");
            Taurus_start = simpleDateFormat.parse("04-21");
            Gemini_start = simpleDateFormat.parse("05-22");
            Cancer_start = simpleDateFormat.parse("06-22");
            Leo_start = simpleDateFormat.parse("07-23");
            Virgo_start = simpleDateFormat.parse("08-24");
            Libra_start = simpleDateFormat.parse("09-24");
            Scorpio_start = simpleDateFormat.parse("10-24");
            Sagittarius_start = simpleDateFormat.parse("11-23");
            Capricorn_start = simpleDateFormat.parse("12-22");
            Aquarius_start = simpleDateFormat.parse("01-21");
            Pisces_start = simpleDateFormat.parse("02-20");
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
        } catch (Exception e) {

        }
    }

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Morn_start = simpleDateFormat.parse("05:00:01");
            Morn_end = simpleDateFormat.parse("08:30:00");
            Morning_start = simpleDateFormat.parse("08:30:01");
            Morning_end = simpleDateFormat.parse("12:00:00");
            Afternoon_start = simpleDateFormat.parse("12:00:01");
            Afternoon_end = simpleDateFormat.parse("17:30:00");
            Dusk_start = simpleDateFormat.parse("17:30:01");
            Dusk_end = simpleDateFormat.parse("20:30:00");
            Night_start = simpleDateFormat.parse("20:30:01");
            Night_end = simpleDateFormat.parse("05:00:00");
        } catch (Exception e) {

        }
    }


    public static int showYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int showMonth() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    public static int showMonthDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int showMaxNowMonth() {
        Calendar c = Calendar.getInstance();
        return c.getActualMaximum(Calendar.DATE);
    }

    public static String showNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    public static String showNowDate2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    public static String showYearMonthDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    public static String showNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    public static String showNowTime2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    public static String showYearMonthDayAddOneDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return simpleDateFormat.format(c.getTime());
    }

    public static String getStringDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String getStringDateMonth(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String showWeekTable(String date, int f) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(date));
            String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
            if (f == 0) {
                switch (mWay) {
                    case "1":
                        mWay = "星期天";
                        break;
                    case "2":
                        mWay = "星期一";
                        break;
                    case "3":
                        mWay = "星期二";
                        break;
                    case "4":
                        mWay = "星期三";
                        break;
                    case "5":
                        mWay = "星期四";
                        break;
                    case "6":
                        mWay = "星期五";
                        break;
                    case "7":
                        mWay = "星期六";
                        break;
                }
            }
            return mWay;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String showDateClass(String thisDate) {
        Timestamp timestamp = new Timestamp(stringToDate(thisDate).getTime());
        Date now = new Date();
        long times = now.getTime() - timestamp.getTime();
        long month = times / ((24 * 60 * 60 * 1000) * 20);
        long day = times / (24 * 60 * 60 * 1000);
        long hour = (times / (60 * 60 * 1000) - day * 24);
        long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuilder stringBuilder = new StringBuilder();
        if (month > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stringBuilder.append(simpleDateFormat.format(timestamp));
        } else if (day > 0) {
            if (day == 1) {
                stringBuilder.append("1天前");
            } else if (day == 2) {
                stringBuilder.append("2天前");
            } else if (day == 3) {
                stringBuilder.append("3天前");
            } else {
                stringBuilder.append(day).append("天前");
            }
        } else if (hour > 0) {
            stringBuilder.append(hour).append("小时前");
        } else if (min > 0) {
            stringBuilder.append(min).append("分钟前");
        } else {
            stringBuilder.append("刚刚");
        }
        return stringBuilder.toString();
    }

    public static Date stringToDate(String time) {
        if (time.contains("T")){
           time = time.replace("T", " ");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDate2(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String sendMath(int i) {
        float a = (float) i / 1000;
        String num = null;
        if (a > 1) {
            String result = String.format("%.1f", a);
            if (result.endsWith("0")) {
                num = String.valueOf(Integer.valueOf(result)) + "k";
            } else {
                num = result + "k";
            }
        } else {
            num = String.valueOf(i);
        }
        return num;
    }

    public static String getConstellation(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        try {
            Date birthdate = simpleDateFormat.parse(date);
            if (birthdate.getTime() >= Aries_start.getTime() && birthdate.getTime() <= Aries_end.getTime()) {
                return "白羊座";
            } else if (birthdate.getTime() >= Taurus_start.getTime() && birthdate.getTime() <= Taurus_end.getTime()) {
                return "金牛座";
            } else if (birthdate.getTime() >= Gemini_start.getTime() && birthdate.getTime() <= Gemini_end.getTime()) {
                return "双子座";
            } else if (birthdate.getTime() >= Cancer_start.getTime() && birthdate.getTime() <= Cancer_end.getTime()) {
                return "巨蟹座";
            } else if (birthdate.getTime() >= Leo_start.getTime() && birthdate.getTime() <= Leo_end.getTime()) {
                return "狮子座";
            } else if (birthdate.getTime() >= Virgo_start.getTime() && birthdate.getTime() <= Virgo_end.getTime()) {
                return "处女座";
            } else if (birthdate.getTime() >= Libra_start.getTime() && birthdate.getTime() <= Libra_end.getTime()) {
                return "天秤座";
            } else if (birthdate.getTime() >= Scorpio_start.getTime() && birthdate.getTime() <= Scorpio_end.getTime()) {
                return "天蝎座";
            } else if (birthdate.getTime() >= Sagittarius_start.getTime() && birthdate.getTime() <= Sagittarius_end.getTime()) {
                return "射手座";
            } else if (birthdate.getTime() >= Capricorn_start.getTime() || birthdate.getTime() <= Capricorn_end.getTime()) {
                return "摩羯座";
            } else if (birthdate.getTime() >= Aquarius_start.getTime() && birthdate.getTime() <= Aquarius_end.getTime()) {
                return "水瓶座";
            } else if (birthdate.getTime() >= Pisces_start.getTime() && birthdate.getTime() <= Pisces_end.getTime()) {
                return "双鱼座";
            }
        } catch (ParseException e) {
            return "";
        }
        return "";
    }

    public static String getConstellationEImg(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        try {
            Date birthdate = simpleDateFormat.parse(date);
            if (birthdate.getTime() >= Aries_start.getTime() && birthdate.getTime() <= Aries_end.getTime()) {
                return "star_aries.png";
            } else if (birthdate.getTime() >= Taurus_start.getTime() && birthdate.getTime() <= Taurus_end.getTime()) {
                return "star_taurus.png";
            } else if (birthdate.getTime() >= Gemini_start.getTime() && birthdate.getTime() <= Gemini_end.getTime()) {
                return "star_gemini.png";
            } else if (birthdate.getTime() >= Cancer_start.getTime() && birthdate.getTime() <= Cancer_end.getTime()) {
                return "star_cancer.png";
            } else if (birthdate.getTime() >= Leo_start.getTime() && birthdate.getTime() <= Leo_end.getTime()) {
                return "star_leo.png";
            } else if (birthdate.getTime() >= Virgo_start.getTime() && birthdate.getTime() <= Virgo_end.getTime()) {
                return "star_virgo.png";
            } else if (birthdate.getTime() >= Libra_start.getTime() && birthdate.getTime() <= Libra_end.getTime()) {
                return "star_libra.png";
            } else if (birthdate.getTime() >= Scorpio_start.getTime() && birthdate.getTime() <= Scorpio_end.getTime()) {
                return "star_scorpio.png";
            } else if (birthdate.getTime() >= Sagittarius_start.getTime() && birthdate.getTime() <= Sagittarius_end.getTime()) {
                return "star_sagittarius.png";
            } else if (birthdate.getTime() >= Capricorn_start.getTime() || birthdate.getTime() <= Capricorn_end.getTime()) {
                return "star_capricorn.png";
            } else if (birthdate.getTime() >= Aquarius_start.getTime() && birthdate.getTime() <= Aquarius_end.getTime()) {
                return "star_aquarius.png";
            } else if (birthdate.getTime() >= Pisces_start.getTime() && birthdate.getTime() <= Pisces_end.getTime()) {
                return "star_pisces.png";
            }
        } catch (ParseException e) {
            return "";
        }
        return "";
    }

    public static String obtainPeriod(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date timedate = simpleDateFormat.parse(date);
            if (timedate.getTime() >= Morn_start.getTime() && timedate.getTime() <= Morn_end.getTime()) {
                return "早晨";
            } else if (timedate.getTime() >= Morning_start.getTime() && timedate.getTime() <= Morning_end.getTime()) {
                return "上午";
            } else if (timedate.getTime() >= Afternoon_start.getTime() && timedate.getTime() <= Afternoon_end.getTime()) {
                return "下午";
            } else if (timedate.getTime() >= Dusk_start.getTime() && timedate.getTime() <= Dusk_end.getTime()) {
                return "黄昏傍晚";
            } else if (timedate.getTime() >= Night_start.getTime() || timedate.getTime() <= Night_end.getTime()) {
                return "夜晚";
            }
        } catch (ParseException e) {
            return "";
        }
        return "";
    }
}
