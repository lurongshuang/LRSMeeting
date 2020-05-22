package com.lrs.lrsmeeting.bean

/**
 * @description 作用:
 * @date: 2020/4/10
 * @author: 卢融霜
 */
class EventBusMeetingListBean(id: Int, listMeetingIds: MutableList<User>) {
    //索引 标识
    var id = id;

    //消息内容
    var listMeetingIds = listMeetingIds;
}