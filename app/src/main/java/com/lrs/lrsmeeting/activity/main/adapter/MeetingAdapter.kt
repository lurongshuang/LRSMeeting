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
        helper?.setText(R.id.tvMeetingPer, item?.description);
        helper?.setText(
            R.id.tvMeetingDate,
            TimeUtils.getInstance().utc2Local(item?.startTime, "yyyy年MM月dd日")
        );
        var llItem: LinearLayout? = helper?.getView(R.id.llItem);
        llItem?.tag = item?.id;
        llItem?.setOnClickListener {
            val id: Long = it.tag as Long;
            val bundle = Bundle();
            bundle.putLong("meetingId", id);
            (super.Mcontext as MainActivity).openAcitivty(MeetingDelActivity().javaClass, bundle)
        }
    }
}