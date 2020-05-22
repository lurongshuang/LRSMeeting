package com.lrs.lrsmeeting.bean

import cn.bmob.v3.BmobObject
import kotlin.properties.Delegates


/**
 * @description 作用:
 * @date: 2020/4/8
 * @author: 卢融霜
 */
class Meeting() : BmobObject() {
    constructor(
        meetingId: String,
        meetingName: String,
        meetingDate: String,
        meetingPerson: String,
        launUserId: String,
        meetingSate: Int
    ) : this() {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingPerson = meetingPerson;
        this.launUserId = launUserId;
        this.meetingSate = meetingSate;
    }

    lateinit var meetingId: String;
    lateinit var meetingName: String;
    lateinit var meetingDate: String;
    lateinit var meetingPerson: String;
    lateinit var launUserId: String;

    /**
     * 0 未开始，1.结束
     */
    var meetingSate: Int = 0;

}