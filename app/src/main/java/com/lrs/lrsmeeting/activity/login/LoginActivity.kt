package com.lrs.lrsmeeting.activity.login

import androidx.databinding.DataBindingUtil
import com.gyf.barlibrary.ImmersionBar
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.login.model.LoginModel
import com.lrs.lrsmeeting.base.BaseActivity
import com.lrs.lrsmeeting.databinding.Login

class LoginActivity : BaseActivity() {
    private lateinit var login: Login;
    private lateinit var lm: LoginModel;

    override fun setView() {
        login = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    override fun loadView(): Int {
        return 0;
    }

    override fun initData() {
        initModel();
        //设置沉浸色为白色
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.textColorWhite, 1F)
            .fitsSystemWindows(false).init()
    }

    override fun clearData() {
    }

    private fun initModel() {
        lm = LoginModel(this);
        login.ln = lm;
    }
}
