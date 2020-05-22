package com.lrs.lrsmeeting.activity.main

import android.content.Intent
import android.graphics.Rect
import android.view.KeyEvent
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import butterknife.BindView
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.login.LoginActivity
import com.lrs.lrsmeeting.activity.main.adapter.MyFragmentPagerAdapter
import com.lrs.lrsmeeting.activity.main.item.MeetingFragment
import com.lrs.lrsmeeting.activity.main.item.MyselfFragment
import com.lrs.lrsmeeting.base.BaseActivity
import com.lrs.lrsmeeting.view.NoScrollViewPager
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    @BindView(R.id.main_viewPager)
    lateinit var mainViewPager: NoScrollViewPager;

    @BindView(R.id.radio0)
    lateinit var radio0: RadioButton

    @BindView(R.id.radio1)
    lateinit var radio1: RadioButton

    @BindView(R.id.mRadioGroupId)
    lateinit var mRadioGroupId: RadioGroup


    private var userId: String? = null;

    /**
     * pageList
     *
     * @return
     */
    private var allFragment: MutableList<Fragment> = ArrayList();
    override fun loadView(): Int {
        return R.layout.activity_main;
    }

    override fun initData() {
        setTitle(false, "首页");
        userId = SharedPreferencesHelper.getPrefString( "userId", null);
        if (userId == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            val task: TimerTask = object : TimerTask() {
                override fun run() {
                    finish()
                }
            }
            val timer = Timer()
            timer.schedule(task, 1 * 1000.toLong())
            return
        } else {
            //初始化page
            initPage()
            //重新计算底部按钮大小
            initButton();
        }
    }


    /**
     * 初始化page
     */
    private fun initPage() {
        val fragmentItem1: MeetingFragment? = MeetingFragment.newInstance()
        val fragmentItem2: MyselfFragment? = MyselfFragment.newInstance()
        allFragment.add(fragmentItem1!!)
        allFragment.add(fragmentItem2!!)
        mainViewPager.adapter = MyFragmentPagerAdapter(supportFragmentManager, allFragment)
        mainViewPager.currentItem = 0
    }

    /**
     * 初始化按钮
     */
    private fun initButton() {
        val rab = arrayOf<RadioButton>(radio0, radio1)
        for (radioButton in rab) {
            val drs = radioButton.compoundDrawables
            val w = (drs[1].minimumWidth / 5.6).toInt()
            val h = (drs[1].minimumHeight / 5.6).toInt()
            val r = Rect(0, 0, w, h)
            drs[1].bounds = r
            radioButton.setCompoundDrawables(null, drs[1], null, null)
        }
        mRadioGroupId.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio0 -> mainViewPager.setCurrentItem(0, false)
                R.id.radio1 -> mainViewPager.setCurrentItem(1, false)
            }
        }
    }

    override fun clearData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
