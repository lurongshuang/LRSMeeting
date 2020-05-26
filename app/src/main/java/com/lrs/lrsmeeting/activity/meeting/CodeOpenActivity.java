package com.lrs.lrsmeeting.activity.meeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lrs.lrsmeeting.R;
import com.lrs.lrsmeeting.base.BaseActivity;

import butterknife.BindView;

public class CodeOpenActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.edCode)
    EditText edCode;
    @BindView(R.id.btMeeting)
    Button btMeeting;


    @Override
    protected int loadView() {
        return R.layout.activity_code_open;
    }

    @Override
    protected void initData() {
        setTitle(true, "会议");
        btMeeting.setOnClickListener(this);

    }

    @Override
    protected void clearData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btMeeting:
                if (edName.getText().toString().isEmpty()) {
                    showToast("请输入昵称");
                    return;
                }
                if (edCode.getText().toString().isEmpty()) {
                    showToast("请输入邀请码");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("userName", edName.getText().toString());
                bundle.putLong("meetingId", Long.parseLong(edCode.getText().toString()));
                openAcitivty(MeetingWebViewActivity.class, bundle);
                break;
        }

    }
}
