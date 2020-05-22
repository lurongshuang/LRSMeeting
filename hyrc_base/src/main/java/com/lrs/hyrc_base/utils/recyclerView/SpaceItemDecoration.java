package com.lrs.hyrc_base.utils.recyclerView;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @description 作用: 横向 列表 设置间距 所以从1开始
 * @date: 2020/5/13
 * @author: 卢融霜
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int index;

    public SpaceItemDecoration(int space, int index) {
        this.space = space;
        this.index = index;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) > index) {
            outRect.top = space;
        }
    }
}
