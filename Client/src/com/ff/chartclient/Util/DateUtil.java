package com.ff.chartclient.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
  日期转换工具类
 */
public class DateUtil {

       /*
         固定格式(yyyy-MM-dd)字符串 转 日期
        */
    public static Date stringToDate(String str) throws ParseException {
        if(str==null){throw new NullPointerException("日期为空");}


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(str);
    }

     /*
        自定义格式的字符串转日期
      */
    public static Date stringToDate(String str,String p) throws ParseException {
        if(str==null)
        {
            throw new NullPointerException("日期为空");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(p);
        return sdf.parse(str);
    }

     /*
         将当前日期转为指定格式的字符串
      */
    public static String dateToString(String p){
        SimpleDateFormat sdf = new SimpleDateFormat(p);
        return sdf.format(new Date());
    }

    /*
    将指定的时间对象 转为指定格式的字符串
     */
    public static String dateToString(Date date ,String p){
        SimpleDateFormat sdf = new SimpleDateFormat(p);
        return sdf.format(date);
    }

    /*
      将long类型的时间戳 转为 指定格式的字符串
     */
    public static String dateToString(long longdate ,String p){
        SimpleDateFormat sdf = new SimpleDateFormat(p);
        return sdf.format(new Date(longdate));
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.dateToString("yyyy年MM月dd日"));
        System.out.println(DateUtil.dateToString("yyyy年MM月dd日 HH:mm:ss"));

        Date  date = new Date(1608206306537L);
        System.out.println(DateUtil.dateToString(date,"yyyy年MM月dd日 HH:mm:ss"));
        System.out.println(DateUtil.dateToString(1608206306537L,"yyyy年MM月dd日 HH:mm:ss"));
    }

}
