package com.ff.chartclient.Util;

/*
  关于字符串操作的方法
 */
public class StringUtil {


    /**
     * 截取文件后缀名
     * @param fileName
     * @return
     */
     public static String subFileType(String fileName){
         if(fileName !=null){
             return fileName.substring(fileName.lastIndexOf(".")+1);
         }
         return null;
     }


}
