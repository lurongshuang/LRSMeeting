package com.lrs.hyrc_base.activity.base;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * create by lrs 2019/10/12
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private BaseViewHolder helper;
    public Context Mcontext;

    public BaseAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.Mcontext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        itemInit(helper, item);

    }

    //初始化item
    protected abstract void itemInit(BaseViewHolder helper, T item);
}
