package com.lrs.lrsmeeting.activity.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @description 作用:
 * @date: 2020/4/8
 * @author: 卢融霜
 */
class MyFragmentPagerAdapter(fm: FragmentManager, list: List<Fragment>) :
    FragmentPagerAdapter(fm) {
    private var list: List<Fragment> = list;

    override fun getItem(position: Int): Fragment {
        return list!![position]
    }

    override fun getCount(): Int {
        return list!!.size
    }
}