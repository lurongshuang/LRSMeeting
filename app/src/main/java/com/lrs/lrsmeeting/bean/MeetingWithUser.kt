package com.lrs.lrsmeeting.bean

import cn.bmob.v3.BmobObject

/**
 * @description 作用:
 * @date: 2020/4/10
 * @author: 卢融霜
 */
class MeetingWithUser(userId: String, meetingId: String) : BmobObject() {
    //用户id
    var userId = userId;

    //会议id
    var meetingId = meetingId;
}