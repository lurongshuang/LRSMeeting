package com.lrs.hyrc_base.activity.base;

import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by a on 2017/9/19.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private String[] titles;

    public MyFragmentPagerAdapter(FragmentManager fm,
                                  List<Fragment> list,
                                  String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    public MyFragmentPagerAdapter(FragmentManager fm,
                                  List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);


    }
}
