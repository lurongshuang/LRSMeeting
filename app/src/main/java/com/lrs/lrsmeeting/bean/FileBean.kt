package com.lrs.lrsmeeting.bean

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile
import java.io.File

/**
 * @description 作用: 用户表
 * @date: 2020/3/24
 * @author: 卢融霜
 */
class FileBean() : BmobObject() {
    constructor(userId: String, fileId: String, fileF: BmobFile) : this() {
        this.userId = userId;
        this.fileId = fileId;
        this.fileF = fileF;
    }

    /**
     * 用户名
     */
    lateinit var userId: String;

    /**
     * 文件id
     */
    lateinit var fileId: String;

    /**
     * 文件
     */
    lateinit var fileF: BmobFile;


}