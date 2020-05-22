package com.lrs.hyrc_base.utils.handler;


import android.os.Handler;
import android.os.Message;

/**
 * @description 作用: 封装 handler
 * @date: 2019/11/5
 * @author: 卢融霜
 */
public class HandlerUtils {
    public static Handler getHandler(final OnHandlerMessage message) {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message1) {
                message.handleMessage(message1);
            }
        };
        return handler;
    }
}
