package com.lrs.lrsmeeting.activity.main.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.chad.library.adapter.base.BaseViewHolder
import com.lrs.hyrc_base.utils.time.TimeUtils
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.main.MainActivity
import com.lrs.lrsmeeting.activity.meeting.MeetingDelActivity
import com.lrs.lrsmeeting.base.BaseAdapter
import com.lrs.lrsmeeting.bean.MeetingBase
import java.text.SimpleDateFormat
import java.util.*


/**
 * @description 作用:
 * @date: 2020/4/8
 * @author: 卢融霜
 */
class MeetingAdapter(resId: Int, context: Context) :
    BaseAdapter<MeetingBase.MeetingsBean>(resId, context) {
    private var notBeginMeeting = -1;
    private var endMeeting = -1;

    fun clearState() {
        endMeeting = -1;
        notBeginMeeting = -1;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun itemInit(helper: BaseViewHolder?, item: MeetingBase.MeetingsBean?) {
        helper?.setText(R.id.tvMeetingName, item?.name);
        helper?.setText(
            R.id.tvMeetingPer,
            TimeUtils.getInstance().utc2Local(
                item?.startTime,
                "MM月dd日"
            ) + " " + TimeUtils.getInstance()
                .utc2Local(
                    item?.startTime,
                    "HH:mm"
                ) + "--" + TimeUtils.getInstance()
                .utc2Local(item?.endTime, "HH:mm")
        );

        var textSate = switchTime(item?.startTime, item?.endTime);
        when (textSate) {
            "进行中" -> {
                helper?.setTextColor(
                    R.id.tvMeetingDate,
                    mContext.resources.getColor(R.color.color_green)
                )
            }
            "未开始" -> {
                helper?.setTextColor(
                    R.id.tvMeetingDate,
                    mContext.resources.getColor(R.color.colorPrimaryDark)
                )
            }
            "已结束" -> {
                helper?.setTextColor(
                    R.id.tvMeetingDate,
                    mContext.resources.getColor(R.color.contents_text)
                )
            }
        }
        helper?.setText(R.id.tvMeetingDate, textSate);
        var llItem: LinearLayout? = helper?.getView(R.id.llItem);
        llItem?.tag = item?.id;
        llItem?.setOnClickListener {
            val id: Long = it.tag as Long;
            val bundle = Bundle();
            bundle.putLong("meetingId", id);
            (super.Mcontext as MainActivity).openAcitivty(MeetingDelActivity().javaClass, bundle)
        }
    }

    fun switchTime(startTime: String?, endTime: String?): String? {
        var stateText = "进行中"
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 0)
        val tdTime =
            SimpleDateFormat("yyyyMMddHHmmss").format(c.time).toLong()
        val startTimeL = TimeUtils.getInstance()
            .utc2Local(startTime, "yyyyMMddHHmmss").toLong()
        val endTimeL = TimeUtils.getInstance()
            .utc2Local(endTime, "yyyyMMddHHmmss").toLong()
        if (tdTime in (startTimeL + 1) until endTimeL) {
            stateText = "进行中"
        } else if (tdTime < startTimeL && tdTime < endTimeL) {
            stateText = "未开始"
        } else if (tdTime > startTimeL && tdTime > endTimeL) {
            stateText = "已结束"
        } else {
            stateText = "未知状态"
        }
        return stateText
    }

}