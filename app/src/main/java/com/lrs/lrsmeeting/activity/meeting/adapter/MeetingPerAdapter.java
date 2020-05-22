package com.lrs.lrsmeeting.activity.meeting.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lrs.lrsmeeting.R;
import com.lrs.lrsmeeting.base.BaseAdapter;
import com.lrs.lrsmeeting.bean.MeetingDel;
import com.lrs.lrsmeeting.url.Config;

/**
 * @description 作用:
 * @date: 2020/5/22
 * @author: 卢融霜
 */
public class MeetingPerAdapter extends BaseAdapter<MeetingDel.MembersBean> {
    public MeetingPerAdapter(int layoutResId, Context context) {
        super(layoutResId, context);
    }

    @Override
    protected void itemInit(BaseViewHolder helper, MeetingDel.MembersBean item) {
        //https://demo.527meeting.com/app/image/image_486726885732342775.jpg

        Glide.with(mContext).load(Config.HOST + (item.getPictureurl().substring(1))).into((ImageView) helper.getView(R.id.ivUserUrl));

        helper.setText(R.id.tvUserName, item.getName());
    }
}
