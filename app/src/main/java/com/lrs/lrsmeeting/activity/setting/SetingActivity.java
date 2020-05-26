package com.lrs.lrsmeeting.activity.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper;
import com.lrs.hyrc_base.utils.thread.ThreadUtils;
import com.lrs.hyrc_base.utils.time.TimeUtils;
import com.lrs.lrsmeeting.R;
import com.lrs.lrsmeeting.activity.login.LoginActivity;
import com.lrs.lrsmeeting.base.BaseActivity;
import com.xuexiang.xui.widget.alpha.XUIAlphaLinearLayout;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class SetingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.xuiUnLogin)
    XUIAlphaLinearLayout xuiUnLogin;

    @BindView(R.id.xuiExit)
    XUIAlphaLinearLayout xuiExit;


    @Override
    protected int loadView() {
        return R.layout.activity_seting;
    }

    @Override
    protected void initData() {
        setTitle(true, "设置");
        xuiUnLogin.setOnClickListener(this);
        xuiExit.setOnClickListener(this);
    }

    @Override
    protected void clearData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xuiUnLogin:
                SharedPreferencesHelper.clearPreference();
                ThreadUtils.timeLapse(1, new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        exitApp();
                        openAcitivty(LoginActivity.class);
                    }
                });
                break;
            case R.id.xuiExit:
                ThreadUtils.timeLapse(1, new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        exitApp();
                    }
                });
                break;
        }
    }
}
