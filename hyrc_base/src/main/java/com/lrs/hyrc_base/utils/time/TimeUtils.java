package com.lrs.hyrc_base.utils.time;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @description 作用:关于时间的工具类
 * @date: 2019/11/20
 * @author: 卢融霜
 */
public class TimeUtils {
    private static TimeUtils timeUtils;

    public static TimeUtils getInstance() {
        if (null == timeUtils) {
            timeUtils = new TimeUtils();
        }
        return timeUtils;
    }

    /**
     * @return //XXXX年XX月XX日 星期X
     */
    public String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear;
        String mMonth;
        String mDay;
        String mWay;
        //获取当前年份
        mYear = String.valueOf(c.get(Calendar.YEAR));
        // 获取当前月份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        // 获取当前月份的日期号码
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

        return mYear + "年" + mMonth + "月" + mDay + "日" + "星期" + getWay(mWay);
    }

    private String getWay(String mWay) {
        String[] ways = {"日", "一", "二", "三", "四", "五", "六"};
        return ways[Integer.parseInt(mWay) - 1];
    }


    /**
     * 当地时间 ---> UTC时间
     *
     * @return
     */
    public String Local2UTC() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    /**
     * 任意时间---> UTC时间
     *
     * @return
     */
    public String Local2UTC(String localTime) {
        //当地时间格式
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormater.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date gpsLocalDate = null;
        try {
            gpsLocalDate = localFormater.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //UTC时间格式
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyyMMddHHmmss");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        String UTCTime = utcFormater.format(gpsLocalDate.getTime());
        return UTCTime;
    }


    /**
     * UTC时间 ---> 当地时间
     *
     * @param utcTime UTC时间
     * @return
     */
    public String utc2Local(String utcTime, String format) {
        //UTC时间格式
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyyMMddHHmmss");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //当地时间格式
        SimpleDateFormat localFormater = new SimpleDateFormat(format);
        localFormater.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }
}
