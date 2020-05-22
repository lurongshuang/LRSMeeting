package com.lrs.lrsmeeting.activity.meeting.onInterface

/**
 * @description 作用:
 * @date: 2020/4/9
 * @author: 卢融霜
 */
interface OnAdapterOnclick {
    /**
     * adapter 点击事件
     *
     * @param position 索引
     */
    fun onClickListener(position: Int, state: Boolean)
}