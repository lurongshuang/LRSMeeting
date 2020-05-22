package com.lrs.lrsmeeting.activity.main.item

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.login.LoginActivity
import com.lrs.lrsmeeting.activity.main.MainActivity
import com.lrs.lrsmeeting.base.LazyLoadingFragment
import java.util.*

/**
 * @description 作用:
 * @date: 2020/4/8
 * @author: 卢融霜
 */
class MyselfFragment : LazyLoadingFragment() {
    companion object {
        private lateinit var fragment: MyselfFragment;
        fun newInstance(): MyselfFragment? {
            val args = Bundle()
            fragment =
                MyselfFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun init(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        rootView?.findViewById<View>(R.id.btOuLogin)?.setOnClickListener {
            doOut();
        };
    }


    override fun onFirstVisibleToUser(): Boolean {
        return false;
    }

    override fun onInvisibleToUser() {

    }

    override fun getLayRes(): Int {
        return R.layout.fragment_myself;
    }

    override fun onVisibleToUser() {
        (activity as MainActivity).setTitle(false, "我的");
    }


    /**
     * 登出
     */
    fun doOut() {
        activity?.let { SharedPreferencesHelper.setPrefString(  "userId", null) }
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                activity?.finish()
            }
        }
        val timer = Timer()
        timer.schedule(task, 1 * 1000.toLong())
    }

}