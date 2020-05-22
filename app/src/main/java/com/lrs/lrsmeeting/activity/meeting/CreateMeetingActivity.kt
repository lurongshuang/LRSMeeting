package com.lrs.lrsmeeting.activity.meeting

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.base.BaseActivity
import com.lrs.lrsmeeting.bean.EventBusMeetingListBean
import com.lrs.lrsmeeting.bean.Meeting
import com.lrs.lrsmeeting.bean.MeetingWithUser
import com.lrs.lrsmeeting.bean.User
import com.lrs.lrsmeeting.utils.date.DateUtils
import com.xuexiang.xui.widget.alpha.XUIAlphaButton
import com.xuexiang.xui.widget.dialog.DialogLoader
import com.xuexiang.xui.widget.flowlayout.FlowLayout
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener
import com.xuexiang.xui.widget.statelayout.StatefulLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList


class CreateMeetingActivity : BaseActivity(), View.OnClickListener {
    @BindView(R.id.fllist)
    lateinit var fllist: FlowLayout

    @BindView(R.id.etMeetingName)
    lateinit var etMeetingName: EditText

    @BindView(R.id.tvMeetingDate)
    lateinit var tvMeetingDate: TextView;

    @BindView(R.id.llMeetingDate)
    lateinit var llMeetingDate: LinearLayout;

    @BindView(R.id.tvMeetingTime)
    lateinit var tvMeetingTime: TextView;

    @BindView(R.id.llMeetingTime)
    lateinit var llMeetingTime: LinearLayout;

    @BindView(R.id.tvMeetingNumber)
    lateinit var tvMeetingNumber: TextView;

    @BindView(R.id.btnMeetingSub)
    lateinit var btnMeetingSub: XUIAlphaButton;

    private var perList = ArrayList<User>();
    private lateinit var user: User;
    private var meetingDate = "";
    override fun loadView(): Int {
        return R.layout.activity_create_meeting;
    }

    override fun initData() {
        setTitle(true, "预约会议");
        var userId = SharedPreferencesHelper.getPrefString( "userId", "");
        var userName = SharedPreferencesHelper.getPrefString( "userName", "");
        var userURL = SharedPreferencesHelper.getPrefString( "userURL", "");
        user = User(userName!!, "", userId!!, userURL!!);
        initPerList();
        EventBus.getDefault().register(this);
        llMeetingDate.setOnClickListener(this);
        llMeetingTime.setOnClickListener(this);
        btnMeetingSub.setOnClickListener(this);

        tvMeetingDate.text = DateUtils.getDateStr(Date(), "yyyy年MM月dd日 HH:mm");
    }

    /*
        参会人员
     */
    private fun initPerList() {
        //添加 自己
        if (perList.size > 0) {
            perList.clear()
        }
        perList.add(user);
        initPerUI();
    }

    private fun initPerUI() {
        if (perList.size > 0) {
            fllist.removeAllViews();
            for ((index, e) in perList.withIndex()) {
                val itemIt = getItem(e, index);
                fllist.addView(itemIt);
            }
        }
//        if (perList.size < 10) {
        fllist.addView(getItem(null, perList.size));
//        }
        tvMeetingNumber.text = "共" + perList.size + "人";
    }

    /**
     * 获取一个用户UI
     */
    private fun getItem(user: User?, index: Int): LinearLayout? {


        val itemView = View.inflate(this, R.layout.include_meeting_per_item, null);
        if (itemView != null) {
            val imageView = itemView.findViewById<ImageView>(R.id.ivUserUrl);
            val textView = itemView.findViewById<TextView>(R.id.tvUserName);
            val linearLayout = itemView.findViewById<LinearLayout>(R.id.llItem);


            var right = resources.getDimensionPixelSize(R.dimen.dp_25);
            var bottom = resources.getDimensionPixelSize(R.dimen.dp_10);
            var params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            if ((index + 1) % 5 == 0) {
                // 每行最后一个
                params.setMargins(0, 0, 0, bottom);
            } else {
                //其他  每行的 1-5之间
                params.setMargins(0, 0, right, bottom);
            }
            linearLayout.layoutParams = params;

            //设置图片圆角角度
            val roundedCorners = RoundedCorners(100)
            val options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
            if (user == null) {
                Glide.with(this).load(resources.getDrawable(R.drawable.ic_add)).apply(options)
                    .into(imageView);
                textView.text = "";
            } else {
                Glide.with(this).load(user.userURL).apply(options).into(imageView);
                textView.text = user?.userName;
            }
            imageView.tag = user;
            imageView.setOnClickListener {
                if (it.tag == null) {
                    val bundle = Bundle();
                    bundle.putStringArrayList(
                        "meetingList",
                        getMeetingList() as java.util.ArrayList<String>?
                    );
                    openAcitivty(SelectActivity().javaClass, bundle)
                } else {
                    val user: User = it.tag as User;
                    if (user.userPhone == this.user.userPhone) {
                        return@setOnClickListener;
                    }
                    fllist.removeView(it);
                    perList.remove(user)
                }
                initPerUI();
            };
            return linearLayout;
        }
        return null;
    }

    fun getMeetingList(): List<String>? {
        var list = ArrayList<String>();
        if (perList.size > 0) {
            perList.forEach {
                list.add(it.userPhone);
            }
            return list;
        }
        return list;
    }

    @Subscribe
    fun eventSbusMessage(eventBusMeetingListBean: EventBusMeetingListBean) {
        when (eventBusMeetingListBean.id) {
            200 -> {
                if (eventBusMeetingListBean.listMeetingIds.size > 0) {
                    perList.clear();
                    eventBusMeetingListBean.listMeetingIds.forEach {
                        perList.add(it);
                    };
                    initPerUI();
                }
            }
        }
    }

    override fun clearData() {
        EventBus.getDefault().unregister(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llMeetingDate -> {
                chooseDate();
            }
            R.id.llMeetingTime -> {
                chooseTimeLong();
            }
            R.id.btnMeetingSub -> {
                submit();
            }

        }
    }

    /**
     * 提交信息
     */
    private fun submit() {
        if (perList.size <= 1) {
            showToast("参会人员至少2人");
            return;
        }
        if (etMeetingName.text.isEmpty()) {
            showToast("请输入会议主题");
            return;
        }
        if (meetingDate.isEmpty()) {
            showToast("请选择开始时间");
            return;
        }
        if (tvMeetingTime.text.isEmpty()) {
            showToast("请选择预估时长");
            return;
        }
        var meeting = Meeting();
        meeting.meetingId = Date().time.toString();
        meeting.meetingDate = meetingDate;
        meeting.meetingName = etMeetingName.text.toString();
        meeting.meetingSate = 0;
        meeting.launUserId = user.userPhone;
        meeting.meetingPerson = getPerOfString();
        loadBaseDialog?.show();
        meeting.save(object : SaveListener<String>() {
            override fun done(t: String?, e: BmobException?) {
                if (e == null) {
                    perList.forEachIndexed { index, user ->
                        var meeWu = MeetingWithUser(user.userPhone, meeting.meetingId);
                        meeWu.save(object : SaveListener<String>() {
                            override fun done(t: String?, e: BmobException?) {

                            }
                        })
                    }
                    loadBaseDialog?.dismiss();
                    showToast("保存成功")
                    finish();
                } else {
                    loadBaseDialog?.dismiss();
                    showToast("保存失败:" + e.message);
                }
            }

        })
    }

    fun getPerOfString(): String {
        var names = "";
        perList.forEachIndexed { index, user ->
            names += user.userName
            if (index + 1 != perList.size) {
                names += ",";
            }
        }
        return names;
    }

    /**
     * 预估时长
     */
    private fun chooseTimeLong() {
        var arr = arrayOf("30分钟", "1个小时", "1个半小时", "2个小时");
        DialogLoader.getInstance().showContextMenuDialog(
            this,
            "预估时长",
            arr,
            DialogInterface.OnClickListener { _, which ->
                tvMeetingTime.text = arr[which];
            });
    }

    /**
     * 选择开会时间
     * */
    private fun chooseDate() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date()
        val mTimePicker = TimePickerBuilder(this,
            OnTimeSelectListener { date, _ ->
                var d =
                    date.year.toString() + "年" + date.month + "月" + date.day + "日 " + date.hours + ":" + date.minutes;
                meetingDate = DateUtils.getDateStr(date, "yyyy年MM月dd日 HH:mm").toString();
                tvMeetingDate.text = meetingDate;
            })
            .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
            .setType(TimePickerType.ALL)
            .setTitleText("时间选择")
            .isDialog(true)
            .setOutSideCancelable(false)
            .setDate(calendar)
            .build();
        mTimePicker.show();
    }
}
