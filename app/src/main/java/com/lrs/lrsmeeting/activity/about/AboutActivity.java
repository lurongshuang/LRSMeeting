package com.lrs.lrsmeeting.activity.about;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lrs.lrsmeeting.R;
import com.lrs.lrsmeeting.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected int loadView() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        setTitle(true, "关于");

    }

    @Override
    protected void clearData() {

    }
}
