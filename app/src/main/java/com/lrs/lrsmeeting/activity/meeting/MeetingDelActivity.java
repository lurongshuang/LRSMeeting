package com.lrs.lrsmeeting.activity.meeting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lrs.hyrc_base.utils.httputils.CallBackUtil;
import com.lrs.hyrc_base.utils.httputils.HyrcHttpUtil;
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper;
import com.lrs.hyrc_base.utils.thread.ThreadUtils;
import com.lrs.hyrc_base.utils.time.TimeUtils;
import com.lrs.lrsmeeting.R;
import com.lrs.lrsmeeting.activity.meeting.adapter.MeetingPerAdapter;
import com.lrs.lrsmeeting.base.BaseActivity;
import com.lrs.lrsmeeting.bean.MeetingBase;
import com.lrs.lrsmeeting.bean.MeetingDel;
import com.lrs.lrsmeeting.url.Config;
import com.lrs.lrsmeeting.utils.text.HtmlTextUtils;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Response;
import retrofit2.Callback;

public class MeetingDelActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ryList)
    RecyclerView ryList;

    @BindView(R.id.btjoinMeeting)
    ButtonView btjoinMeeting;

    @BindView(R.id.sfLayout)
    StatefulLayout sfLayout;

    String sid;
    Long meetingId;
    MeetingPerAdapter adapter;
    private int userId;
    //是否为 支持人
    private boolean isLaunUser;

    @Override
    protected int loadView() {
        return R.layout.activity_meeting_del;
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
        loadDa();
    }

    @Override
    protected void initData() {
        setTitle(true, "会议详情");
        sid = SharedPreferencesHelper.getPrefString("SID", "");
        meetingId = getIntent().getExtras().getLong("meetingId");
        userId = Integer.parseInt(SharedPreferencesHelper.getPrefString("userId", ""));
        LinearLayoutManager gm = new LinearLayoutManager(this);
        gm.setOrientation(GridLayoutManager.VERTICAL);
        ryList.setLayoutManager(gm);
        adapter = new MeetingPerAdapter(R.layout.include_meeting_per_item, this);
        ryList.setAdapter(adapter);
        btjoinMeeting.setOnClickListener(this);
        getData();

    }

    private String[] param = {"会议主题", "主持人", "会议日期", "会议时间", "参会人数", "会议状态"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void intiUI(MeetingDel response) {
        if (userId == response.getMeeting().getFrom().getId()) {
            isLaunUser = true;
        }
        String[] parText = {response.getMeeting().getName(), response.getMeeting().getFrom().getName(),
                TimeUtils.getInstance().utc2Local(response.getMeeting().getStartTime(), "yyyy年MM月dd日"),
                TimeUtils.getInstance().utc2Local(response.getMeeting().getStartTime(), "HH:mm") + "--" + TimeUtils.getInstance().utc2Local(response.getMeeting().getEndTime(), "HH:mm"),
                response.getMembers().size() + "人", switchTime(response.getMeeting().getStartTime(), response.getMeeting().getEndTime()),
                response.getMeeting().getDescription()};

        for (int i = 0; i < param.length; i++) {
            View view = View.inflate(this, R.layout.activity_meeting_item, null);
            TextView tvName = view.findViewById(R.id.tvName);
            TextView tvText = view.findViewById(R.id.tvText);
            tvName.setText(param[i]);
            tvText.setText(parText[i]);
            adapter.addHeaderView(view);
            if (i == param.length - 1) {
                if (satesId == 0) {
                    tvText.setTextColor(getResources().getColor(R.color.color_green));
                } else if (satesId == 1) {
                    tvText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    tvText.setTextColor(getResources().getColor(R.color.contents_text));
                }
            }
        }
        //会议议题
        if (response.getTopics() != null && response.getTopics().trim().length() > 0) {
            String textYt = "";
            try {
                JSONArray jsonArray = new JSONArray(response.getTopics());
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        textYt += jsonArray.getJSONObject(i).getString("title");
                        if (i != jsonArray.length() - 1) {
                            textYt += ", ";
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (textYt.trim().length() > 0) {
                View viewYT = View.inflate(this, R.layout.activity_meeting_item, null);
                TextView tvName = viewYT.findViewById(R.id.tvName);
                TextView tvText = viewYT.findViewById(R.id.tvText);
                tvName.setText("会议议题");
                tvText.setText(textYt);
                adapter.addHeaderView(viewYT, 1);
            }
        }

        View view1 = View.inflate(this, R.layout.include_metting_text, null);
        TextView tvStateTitle = view1.findViewById(R.id.tvStateTitle);
        tvStateTitle.setText("会议简述");
        adapter.addHeaderView(view1);

        View view1_text = View.inflate(this, R.layout.include_metting_text, null);
        TextView textView = view1_text.findViewById(R.id.tvStateTitle);
        textView.setText(HtmlTextUtils.getInstance().htmlDecode(response.getMeeting().getDescription()));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
        adapter.addHeaderView(view1_text);


        View view2 = View.inflate(this, R.layout.include_metting_text, null);
        TextView tvStateTitle1 = view2.findViewById(R.id.tvStateTitle);
        tvStateTitle1.setText("参会人员");
        adapter.addHeaderView(view2);


        View view3 = View.inflate(this, R.layout.include_meeting_list, null);
        RecyclerView ryPerList = view3.findViewById(R.id.ryPerList);
        GridLayoutManager gm = new GridLayoutManager(this, 4);
        gm.setOrientation(GridLayoutManager.VERTICAL);
        ryPerList.setLayoutManager(gm);
        MeetingPerAdapter adapterPer = new MeetingPerAdapter(R.layout.include_meeting_per_item, this);
        ryPerList.setAdapter(adapterPer);
        //参会人员
        if (response.getMembers().size() > 0) {
            adapterPer.addData(response.getMembers());
        }
        adapter.addHeaderView(view3);

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
                    Bundle bundle = getIntent().getExtras();
                    bundle.putBoolean("isLaunUser", isLaunUser);
                    openAcitivty(MeetingWebViewActivity.class, bundle);
                } else {
                    showToast((satesId == 1 ? "会议未开始" : "会议已结束"));
                }
                break;
        }
    }
}
