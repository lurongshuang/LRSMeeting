package com.lrs.hyrc_base;

import android.app.Activity;
import android.app.Application;

import com.xuexiang.xui.XUI;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 作用: baseApplication
 * @date: 2020/5/11
 * @author: 卢融霜
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication mInstance;
    private List<Activity> mActivities = new ArrayList<Activity>();

    /**
     * 单例模式中获取唯一的ExitApplication 实例
     *
     * @return applicationCon
     */
    public static BaseApplication getInstance() {
        return mInstance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
        initApp();
    }

    private void init() {
        //初始化UI框架
        XUI.init(this);
        //开启UI框架调试日志W
        XUI.debug(false);


    }
    protected abstract void initApp();
    /**
     * 把Activity加入历史堆栈
     */
    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 结束
     */
    public void exit() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
        System.exit(0);
    }
}
