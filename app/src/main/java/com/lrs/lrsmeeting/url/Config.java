package com.lrs.lrsmeeting.url;

/**
 * @description 作用:
 * @date: 2020/5/21
 * @author: 卢融霜
 */
public interface Config {
    /**
     * host
     */
    String HOST = "https://demo.527meeting.com/app/";
    /**
     * 登录
     */
    String CURRENCY = HOST + "account";
    /**
     * 房间列表
     */
    String ROOM_LIST = HOST + "meeting";
    /**
     * 会议详情
     */
    String MEETING_DEL = HOST + "meeting";

}
