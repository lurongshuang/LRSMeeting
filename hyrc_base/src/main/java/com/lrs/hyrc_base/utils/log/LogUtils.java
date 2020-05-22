package com.lrs.hyrc_base.utils.log;

import android.util.Log;

public class LogUtils {

    private static boolean isLog = false;
    private final static String LOG_TAG = "com.hyrc.cloudWater";

    public static void logI(String tag, String msg) {
        if (isLog) {
            Log.i(tag, msg);
        }
    }

    public static void logW(String tag, String msg) {
        if (isLog) {
            Log.d(tag, msg);
        }
    }

    public static void logE(String tag, String msg) {
        if (isLog && !msg.isEmpty()) {
            Log.e(tag, msg);
        }
    }

    public static void logV(String tag, String msg) {
        if (isLog) {
            Log.v(tag, msg);
        }
    }

    public static void error(String msg, Throwable throwable) {
        if(throwable == null) {
            Log.e(LOG_TAG, msg);
        } else {
            Log.e(LOG_TAG, msg, throwable);
        }
    }
}
