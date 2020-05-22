package com.lrs.lrsmeeting.utils.date

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * @description 作用:
 * @date: 2020/4/10
 * @author: 卢融霜
 */
object DateUtils {
    /**
     * Date 转 年月日时分秒
     */
    fun getDateStr(date: Date?, format: String?): String? {
        var format = format
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss"
        }
        val formatter = SimpleDateFormat(format)
        return formatter.format(date)
    }

    /**
     * 年月日时分秒 转Date
     */
    fun parseServerTime(serverTime: String?, format: String?): Date? {
        var format = format
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss"
        }
        val sdf = SimpleDateFormat(format, Locale.CHINESE)
        sdf.timeZone = TimeZone.getTimeZone("GMT+8:00")
        var date: Date? = null
        try {
            date = sdf.parse(serverTime)
        } catch (e: Exception) {
            Log.e("DateUtils", e.message)
        }
        return date
    }
}