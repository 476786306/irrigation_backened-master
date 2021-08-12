package com.libv.util;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DataUtil {

    public static int  hexToDecimal(String hex){
        BigInteger bigint=new BigInteger(hex, 16);
        int numb=bigint.intValue();
        return numb;
    }



    public static void main(String... args) {
        Scanner input = new Scanner(System.in);
        String content = input.nextLine();
        if(!content.matches("[0-9a-fA-F]*")){
            System.out.println("输入不匹配");
            System.exit(-1);
        }
        //将全部的小写转化为大写
        content = content.toUpperCase();
        System.out.println(hexToDecimal(content));

    }

    public static boolean  isEffectiveTime(String nowStr,String startStr, String endStr) {
        String format = "HH:mm:ss";
        //Date nowTime = new SimpleDateFormat(format).parse("09:27:00");
        DateFormat dateFormat = DateFormat.getTimeInstance();//获取时分秒
        Date nowTime = null;
        try {
            nowTime = new SimpleDateFormat(format).parse(nowStr);
            Date startTime = new SimpleDateFormat(format).parse(startStr);
            Date endTime = new SimpleDateFormat(format).parse(endStr);
           return isEffectiveDate(nowTime, startTime, endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
