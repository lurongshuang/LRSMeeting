package com.lrs.lrsmeeting.activity.meeting.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.meeting.onInterface.OnAdapterOnclick
import com.lrs.lrsmeeting.base.BaseAdapter
import com.lrs.lrsmeeting.bean.User
import java.util.*

/**
 * @description 作用:
 * @date: 2020/4/9
 * @author: 卢融霜
 */
class SelectAdapter(layoutResId: Int, context: Context) : BaseAdapter<User>(layoutResId, context) {
    private var myId: String? = null

    /**
     * 1 为选中 0为未选中
     */
    private val map = HashMap<String, Int>()
    private var adapterOnclick: OnAdapterOnclick? = null


    fun getMap(): HashMap<String, Int>? {
        return map
    }

    fun getSelectNum(): Int {
        var selectNum = 0
        for ((_, value) in map) {
            if (value == 1) {
                selectNum += 1
            }
        }
        return selectNum
    }

    fun getSelectUser(): List<String?>? {
        val selectUser: MutableList<String?> =
            ArrayList()
        val index = 0
        for ((key, value) in map) {
            if (value == 1) {
                selectUser.add(key)
            }
        }
        selectUser.add(myId)
        return selectUser
    }

    fun getSelectUserOfUser(): List<User?>? {
        val selectUser: MutableList<User?> =
            ArrayList()
//        selectUser.add(getUserById(myId!!));
        for ((key, value) in map) {
            if (value == 1) {
                selectUser.add(getUserById(key))
            }
        }
        return selectUser
    }

    private fun getUserById(userId: String): User? {
        data.forEach {
            if (it.userPhone == userId) {
                return it;
            }
        }
        return null;
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun itemInit(helper: BaseViewHolder, item: User) {
        helper.setText(R.id.item_tv_qz, item.userName)
        val linearLayout = helper.getView<LinearLayout>(R.id.linearLayout_it)
        if (item.userPhone.equals(myId)) {
            helper.setImageResource(R.id.ivchecnd, R.drawable.rc_voip_icon_checkbox_checked)
        } else {
            val id = map.getOrDefault(item.userPhone, -1)
            if (id == 1) {
                helper.setImageResource(R.id.ivchecnd, R.drawable.rc_voip_icon_checkbox_hover)
                linearLayout.setTag(R.id.selectState, true)
            } else if (id == -1) {
                map[item.userPhone] = 0
                helper.setImageResource(R.id.ivchecnd, R.drawable.rc_voip_icon_checkbox_normal)
                linearLayout.setTag(R.id.selectState, false)
            } else {
                helper.setImageResource(R.id.ivchecnd, R.drawable.rc_voip_icon_checkbox_normal)
                linearLayout.setTag(R.id.selectState, false)
            }
        }
        Glide.with(super.Mcontext).load(item.userURL)
            .into(helper.getView<View>(R.id.item_iv_qz) as ImageView)
        linearLayout.setTag(R.id.selectPosition, helper.adapterPosition)
        linearLayout.setTag(R.id.selectId, item.userPhone)
        linearLayout.setOnClickListener(View.OnClickListener { v ->
            val thisId = v.getTag(R.id.selectId) as String
            val imageView =
                v.findViewById<ImageView>(R.id.ivchecnd)
            if (thisId == myId) {
                imageView.setImageResource(R.drawable.rc_voip_icon_checkbox_checked)
                return@OnClickListener
            }
            val position = v.getTag(R.id.selectPosition) as Int
            val state = v.getTag(R.id.selectState) as Boolean
            if (!state) {
                imageView.setImageResource(R.drawable.rc_voip_icon_checkbox_hover)
            } else {
                imageView.setImageResource(R.drawable.rc_voip_icon_checkbox_normal)
            }
            linearLayout.setTag(R.id.selectState, !state)
            map[thisId] = if (!state) 1 else 0
            adapterOnclick?.onClickListener(position, !state)
        })
    }

    fun setUserId(userId: String?) {
        myId = userId
    }

    fun setAdapterOnclick(onclick: OnAdapterOnclick?) {
        adapterOnclick = onclick
    }
}