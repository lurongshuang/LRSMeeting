package com.lrs.hyrc_base.activity.base;


import android.view.View;
import android.widget.LinearLayout;

import com.lrs.hyrc_base.R;
import com.lrs.hyrc_base.R2;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.statelayout.CustomStateOptions;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * create by lrs 列表Activity 父类 继承 使得快速开发一个列表Activity  只去关心你想关心的  剩下交给我
 */
public abstract class ListBaseActivity extends HyrcBaseActivity {
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.ll_stateful)
    StatefulLayout statefulLayout;
    private BaseAdapter adapter;
//    @BindView(R.id.ll_toolbar_layout_search)
//    RelativeLayout ll_toolbar_layout_search;
    @BindView(R2.id.ll_toolbar_layout)
    LinearLayout ll_toolbar_layout;

    @Override
    public int loadView() {
        return R.layout.listbase_activity;
    }

    /**
     * 配置 上拉 下拉
     */
    public void setRefreshData() {
        Refresh(true);
        LoadMore(true);
    }

    /**
     * 是否启用上拉加载
     */
    public void LoadMore(boolean state) {
        refreshLayout.setEnableLoadMore(state);
    }

    /**
     * 是否启用下拉刷新
     */
    public void Refresh(boolean state) {
        refreshLayout.setEnableRefresh(state);
    }

    /**
     * 是否停止继续上拉
     */
    public void setNomoreData(boolean state) {
        if (refreshLayout != null) {
            refreshLayout.setNoMoreData(state);
        }
    }


    @Override
    public void initData() {
//        isShowSearchTitle(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setEnableAutoLoadMore(false);
        setRefreshData();
        adapter = initAdapter(adapter);
        recyclerView.setAdapter(adapter);
        loadData(adapter);
        /**
         * 下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listonRefresh(refreshLayout, recyclerView);
                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    recyclerView.removeAllViews();
                }
                if (adapter != null && adapter.getItemCount() > 0) {
                    adapter.getData().clear();
                    adapter.notifyDataSetChanged();
                }
                loadData(adapter);
                finishRefresh();
            }
        });
        /**
         * 上拉加载
         */
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                listOnLoadMore(refreshLayout, recyclerView);
                loadData(adapter);
                finishLoadMore();

            }

        });
    }

    /**
     * 刷新完成
     */
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    /**
     * 加载完成
     */
    public void finishLoadMore() {
        refreshLayout.finishLoadMore();
    }

    /**
     * 无更多数据
     */
    public void finishRefreshWithNoMoreData() {
        refreshLayout.finishRefreshWithNoMoreData();
    }

    /**
     * 显示内容
     */
    public void showContent() {
        if (statefulLayout != null) {
            statefulLayout.showContent();
        }
    }

    /**
     * 显示加载中
     *
     * @param message 提示信息
     */
    public void showLoading(String message) {
        if (message != null && statefulLayout != null) {
            statefulLayout.showLoading(message);
        }
    }

    /**
     * 显示加载中
     */
    public void showLoading() {
        if (statefulLayout != null) {
            statefulLayout.showLoading();
        }
    }

    /**
     * 显示暂无数据
     */
    public void showEmpty() {
        if (statefulLayout != null) {
            statefulLayout.showEmpty();
        }
    }

    /**
     * 显示暂无数据
     *
     * @param message 自定义信息
     */
    public void showEmpty(String message) {
        if (message != null && statefulLayout != null) {
            statefulLayout.showEmpty(message);
        }
    }

    /**
     * 显示错误
     *
     * @param clickListener 点击重试按钮
     */
    public void showError(View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null) {
            statefulLayout.showError(clickListener);
        }
    }

    /**
     * 显示错误
     *
     * @param message       自定义提示信息
     * @param clickListener 点击重试按钮
     */
    public void showError(String message, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null) {
            statefulLayout.showError(message, clickListener);
        }
    }

    /**
     * 显示错误
     *
     * @param message       自定义提示信息
     * @param buttonText    重试按钮提示信息
     * @param clickListener 点击按钮事件
     */
    public void showError(String message, String buttonText, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null && buttonText != null) {
            statefulLayout.showError(message, buttonText, clickListener);
        }
    }

    /**
     * 显示网络离线
     *
     * @param clickListener 点击按钮事件
     */
    public void showOffline(View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null) {
            statefulLayout.showOffline(clickListener);
        }
    }

    /**
     * 显示网络离线
     *
     * @param message       提示消息
     * @param clickListener 点击事件
     */
    public void showOffline(String message, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null) {
            statefulLayout.showOffline(message, clickListener);
        }
    }

    /**
     * 显示网络离线
     *
     * @param message       提示消息
     * @param buttonText    按钮显示信息
     * @param clickListener 点击事件
     */
    public void showOffline(String message, String buttonText, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null && buttonText != null) {
            statefulLayout.showOffline(message, buttonText, clickListener);
        }
    }

    /**
     * 显示自定义布局
     *
     * @param options 自定义布局
     */
    public void showCustom(final CustomStateOptions options) {
        if (statefulLayout != null && options != null) {
            statefulLayout.showCustom(options);
        }
    }

    /**
     * 下拉刷新
     * @param refreshLayout
     * @param recyclerView
     */
    protected abstract void listonRefresh(RefreshLayout refreshLayout, RecyclerView recyclerView);

    /**
     * 上拉加载
     * @param refreshLayout
     * @param recyclerView
     */
    protected abstract void listOnLoadMore(RefreshLayout refreshLayout, RecyclerView recyclerView);

    /**
     *  初始化adapter
     * @param adapter
     * @return
     */
    protected abstract BaseAdapter initAdapter(BaseAdapter adapter);

    /**
     * 加载数据
     */
    protected abstract void loadData(BaseAdapter adapter);
}
