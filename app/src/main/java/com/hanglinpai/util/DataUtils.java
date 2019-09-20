package com.hanglinpai.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chihai
 * @function Created on 2017/6/14.
 */

public class DataUtils {
    /*
获取当前年
*/
    public static String getCurrentData0(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(time);
    }
    /*
 获取当前日期
  */
    public static String getCurrentData1(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
        return format.format(time);
    }
    /*
    获取当前日期
     */
    public static String getCurrentData(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }
    /*
    获取当前日期
     */
    public static String getCurrentDataSelect2(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(new Date());
    }
    /*
获取当前月
 */
    public static String getCurrentDataSelect(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
        return format.format(new Date());
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        long lt = new Long(s);
        Date date = new Date(lt);// 时间戳
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String getDate(){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        res = simpleDateFormat.format(new Date());
        return res;
    }
    /*
       * 将时间戳转换为时间
       */
    public static String getMonth(){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        res = simpleDateFormat.format(new Date());
        return res;
    }
    /*
   * 将时间戳转换为时间
   */
    public static String getYMD(){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        res = simpleDateFormat.format(new Date());
        return res;
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate2(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);// 时间戳
        res = simpleDateFormat.format(date);
        return res;
    }

    //根据时间获取星期
    public static String getWeek() {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(getCurrentData()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int current_week = c.get(Calendar.DAY_OF_WEEK);
        if (current_week == 1) {
            Week += "天";
        }
        if (current_week == 2) {
            Week += "一";
        }
        if (current_week == 3) {
            Week += "二";
        }
        if (current_week == 4) {
            Week += "三";
        }
        if (current_week == 5) {
            Week += "四";
        }
        if (current_week == 6) {
            Week += "五";
        }
        if (current_week == 7) {
            Week += "六";
        }
        return Week;
    }

    //根据时间获取星期
    public static String getWeek(String pTime) {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int current_week = c.get(Calendar.DAY_OF_WEEK);
        if (current_week == 1) {
            Week += "天";
        }
        if (current_week == 2) {
            Week += "一";
        }
        if (current_week == 3) {
            Week += "二";
        }
        if (current_week == 4) {
            Week += "三";
        }
        if (current_week == 5) {
            Week += "四";
        }
        if (current_week == 6) {
            Week += "五";
        }
        if (current_week == 7) {
            Week += "六";
        }
        return Week;
    }
    //根据时间获取星期
    public static String getWeek(String pTime,String str) {
        String Week = str;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int current_week = c.get(Calendar.DAY_OF_WEEK);
        if (current_week == 1) {
            Week += "天";
        }
        if (current_week == 2) {
            Week += "一";
        }
        if (current_week == 3) {
            Week += "二";
        }
        if (current_week == 4) {
            Week += "三";
        }
        if (current_week == 5) {
            Week += "四";
        }
        if (current_week == 6) {
            Week += "五";
        }
        if (current_week == 7) {
            Week += "六";
        }
        return Week;
    }

    //根据时间获取星期
    public static String getScheWeek(String pTime,String str) {
        String Week = str;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int current_week = c.get(Calendar.DAY_OF_WEEK);
        if (current_week == 1) {
            Week += "天";
        }
        if (current_week == 2) {
            Week += "一";
        }
        if (current_week == 3) {
            Week += "二";
        }
        if (current_week == 4) {
            Week += "三";
        }
        if (current_week == 5) {
            Week += "四";
        }
        if (current_week == 6) {
            Week += "五";
        }
        if (current_week == 7) {
            Week += "六";
        }
        return Week;
    }
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
 * 将时间转换为时间戳
 */
    public static String dateToStamp2(String s)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = (date.getTime());
        res = String.valueOf(ts);
        return res;
    }
    /*
* 将时间转换为时间戳
*/
    public static String dateToStamp3(String s)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = (date.getTime());
        res = String.valueOf(ts);
        return res;
    }
    public static String dateToStamp4(String s)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = (date.getTime());
        res = String.valueOf(ts);
        return res;
    }
    /*
  * 将时间戳转换为时间
  */
    public static String stampToDate3(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        long lt = new Long(s);
        Date date = new Date(lt);// 时间戳
        res = simpleDateFormat.format(date);
        return res;
    }
}
