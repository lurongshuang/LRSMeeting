package com.lrs.lrsmeeting.activity.meeting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.DnsResolver;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lrs.hyrc_base.utils.httputils.CallBackUtil;
import com.lrs.hyrc_base.utils.httputils.HyrcHttpUtil;
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper;
import com.lrs.hyrc_base.utils.time.TimeUtils;
import com.lrs.lrsmeeting.R;
import com.lrs.lrsmeeting.activity.meeting.adapter.MeetingPerAdapter;
import com.lrs.lrsmeeting.base.BaseActivity;
import com.lrs.lrsmeeting.bean.MeetingBase;
import com.lrs.lrsmeeting.bean.MeetingDel;
import com.lrs.lrsmeeting.url.Config;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;
import retrofit2.Callback;

public class MeetingDelActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tvMeetingName)
    TextView tvMeetingName;

    @BindView(R.id.llMeetingDate)
    LinearLayout llMeetingDate;

    @BindView(R.id.tvMeetingLaunUser)
    TextView tvMeetingLaunUser;

    @BindView(R.id.tvMeetingDate)
    TextView tvMeetingDate;

    @BindView(R.id.tvMeetingTime)
    TextView tvMeetingTime;

    @BindView(R.id.tvMeetingNumber)
    TextView tvMeetingNumber;

    @BindView(R.id.tvMeetingState)
    TextView tvMeetingState;

    @BindView(R.id.rylist)
    RecyclerView rylist;

    @BindView(R.id.btjoinMeeting)
    ButtonView btjoinMeeting;
    @BindView(R.id.sfLayout)
    StatefulLayout sfLayout;

    String sid;
    Long meetingId;
    MeetingPerAdapter adapter;

    @Override
    protected int loadView() {
        return R.layout.activity_meeting_del2;
    }

    private void loadDa() {
        sfLayout.showLoading();
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("meetingId", meetingId);
            jsonObject.put("method", "getMeetingById");
            jsonObject.put("SID", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("params", jsonObject.toString());

        HyrcHttpUtil.httpPost(Config.MEETING_DEL, map, new CallBackUtil<MeetingDel>() {

            @Override
            public MeetingDel onParseResponse(Call call, Response response) {
                try {
                    return new Gson().fromJson(response.body().string(), MeetingDel.class);
                } catch (IOException e) {

                }
                sfLayout.showError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDa();
                    }
                });
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e) {
                sfLayout.showError(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadDa();
                    }
                });
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(MeetingDel response) {
                if (response != null && response.getResult() == 0) {
                    intiUI(response);
                } else {
                    sfLayout.showError(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadDa();
                        }
                    });
                }
            }
        });
    }

    private void getData() {
        btjoinMeeting.setOnClickListener(this);
        loadDa();
    }

    @Override
    protected void initData() {
        setTitle(true, "会议详情");
        GridLayoutManager gm = new GridLayoutManager(this, 4);
        gm.setOrientation(GridLayoutManager.VERTICAL);
        rylist.setLayoutManager(gm);
        adapter = new MeetingPerAdapter(R.layout.include_meeting_per_item, this);
        rylist.setAdapter(adapter);
        sid = SharedPreferencesHelper.getPrefString("SID", "");
        meetingId = getIntent().getExtras().getLong("meetingId");
        getData();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void intiUI(MeetingDel response) {
        tvMeetingName.setText(response.getMeeting().getName());
        tvMeetingLaunUser.setText(response.getMeeting().getFrom().getName());
        tvMeetingDate.setText(TimeUtils.getInstance().utc2Local(response.getMeeting().getStartTime(), "yyyy年MM月dd日"));
        tvMeetingTime.setText(TimeUtils.getInstance().utc2Local(response.getMeeting().getStartTime(), "HH:mm") + "--" + TimeUtils.getInstance().utc2Local(response.getMeeting().getEndTime(), "HH:mm"));
        tvMeetingNumber.setText(response.getMembers().size() + "人");
        tvMeetingState.setText(switchTime(response.getMeeting().getStartTime(), response.getMeeting().getEndTime()));
        //参会人员
        if (response.getMembers().size() > 0) {
            adapter.addData(response.getMembers());
        }
//        if (satesId == 0) {
//            btjoinMeeting.setSolidColor(R.color.colorPrimary);
//        } else {
//            btjoinMeeting.setSolidColor(R.color.f959595);
//        }
        sfLayout.showContent();
    }

    @Override
    protected void clearData() {

    }

    /**
     * 0 进行中 1 未开始  2 已结束
     */
    private int satesId = 0;

    public String switchTime(String startTime, String endTime) {
        String stateText = "进行中";
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 0);
        Long tdTime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime()));
        Long startTimeL = Long.parseLong(TimeUtils.getInstance().utc2Local(startTime, "yyyyMMddHHmmss"));
        Long endTimeL = Long.parseLong(TimeUtils.getInstance().utc2Local(endTime, "yyyyMMddHHmmss"));

        if (tdTime > startTimeL && tdTime < endTimeL) {
            stateText = "进行中";
            satesId = 0;
        } else if (tdTime < startTimeL && tdTime < endTimeL) {
            stateText = "未开始";
            satesId = 1;
        } else if (tdTime > startTimeL && tdTime > endTimeL) {
            stateText = "已结束";
            satesId = 2;
        } else {
            stateText = "未知状态";
            satesId = -1;
        }

        return stateText;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btjoinMeeting:
                if (satesId == 0) {
                    openAcitivty(MeetingWebViewActivity.class, getIntent().getExtras());
                } else {
                    showToast((satesId == 1 ? "会议未开始" : "会议已结束"));
                }
                break;
        }
    }
}
