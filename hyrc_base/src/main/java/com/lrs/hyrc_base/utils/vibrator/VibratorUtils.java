package com.lrs.hyrc_base.utils.vibrator;

import android.content.Context;
import android.os.Vibrator;

/**
 * @description 作用: 震动工具类
 * @date: 2019/11/21
 * @author: 卢融霜
 */
public class VibratorUtils {
    public  static  VibratorUtils vibratorUtils;

    public static VibratorUtils getVibrator() {
        if (vibratorUtils == null) {
            vibratorUtils = new VibratorUtils();
        }

        return vibratorUtils;
    }

    public Vibrator vibrator;

    /**
     * 只震动一下
     */
    public void setVibratorOnly(Context context,int milli) {
        if (vibrator == null) {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        vibrator.vibrate(milli);
    }

    /**
     * 结束震动
     */
    public void cancel() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
