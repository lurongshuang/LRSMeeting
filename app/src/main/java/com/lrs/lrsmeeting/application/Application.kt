package com.lrs.lrsmeeting.application

import android.app.Application
import androidx.multidex.MultiDex
import cn.bmob.v3.Bmob
import com.lrs.hyrc_base.BaseApplication
import com.lrs.hyrc_base.activity.base.HyrcBaseActivity
import com.lrs.lrsmeeting.base.BaseActivity
import com.xuexiang.xui.XUI

/**
 * @description 作用: 应用实例
 * @date: 2020/4/7
 * @author: 卢融霜
 */
class Application : BaseApplication() {
    private lateinit var application: com.lrs.lrsmeeting.application.Application;

    override fun initApp() {
        application = this;
        MultiDex.install(this);
//        XUI.init(this);
//        XUI.debug(true);
        //初始化云存储
        Bmob.initialize(this, "1ad4db4503319e703ac4127b78827df5");
    }


    public fun getApplication(): Application {
        return application;
    }
}