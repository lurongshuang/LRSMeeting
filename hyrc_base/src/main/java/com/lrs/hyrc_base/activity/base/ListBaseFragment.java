package com.lrs.hyrc_base.activity.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lrs.hyrc_base.R;
import com.lrs.hyrc_base.R2;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.statelayout.CustomStateOptions;
import com.xuexiang.xui.widget.statelayout.StatefulLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public abstract class ListBaseFragment extends LazyLoadingFragment {
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.ll_stateful)
    StatefulLayout statefulLayout;
    private BaseAdapter adapter;


    @Override
    protected boolean onFirstVisibleToUser() {
        return false;
    }

    @Override
    protected void onVisibleToUser() {

    }

    @Override
    protected void onInvisibleToUser() {

    }

    @Override
    protected int getLayRes() {
        return R.layout.listbase_fragment;
    }

    @Override
    protected void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
    }

    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        refreshLayout.setEnableAutoLoadMore(false);
        setRefreshData();
        adapter = initAdapter(adapter);
        recyclerView.setAdapter(adapter);
        loadData(adapter);
        /**
         * ????????????
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listonRefresh(refreshLayout, recyclerView);
                clearDatas();
                loadData(adapter);
                finishRefresh();
            }
        });
        /**
         * ????????????
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

    //????????????
    public void clearDatas() {
        if (recyclerView != null && recyclerView.getChildCount() > 0) {
            recyclerView.removeAllViews();
        }
        if (adapter != null && adapter.getItemCount() > 0) {
            adapter.getData().clear();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * ?????? ?????? ??????
     */
    public void setRefreshData() {
        Refresh(true);
        LoadMore(true);
    }

    /**
     * ????????????????????????
     */
    public void LoadMore(boolean state) {
        refreshLayout.setEnableLoadMore(state);
    }

    /**
     * ????????????????????????
     */
    public void Refresh(boolean state) {
        refreshLayout.setEnableRefresh(state);
    }

    /**
     * ????????????
     */
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    /**
     * ????????????
     */
    public void finishLoadMore() {
        refreshLayout.finishLoadMore();
    }

    /**
     * ???????????????
     */
    public void finishRefreshWithNoMoreData() {
        refreshLayout.finishRefreshWithNoMoreData();
    }

    /**
     * ????????????
     */
    public void showContent() {
        if (statefulLayout != null) {
            statefulLayout.showContent();
        }
    }

    /**
     * ????????????????????????
     */
    public void setNomoreData(boolean state) {
        if (refreshLayout != null) {
            refreshLayout.setNoMoreData(state);
        }
    }

    /**
     * ???????????????
     *
     * @param message ????????????
     */
    public void showLoading(String message) {
        if (message != null && statefulLayout != null) {
            statefulLayout.showLoading(message);
        }
    }

    /**
     * ???????????????
     */
    public void showLoading() {
        if (statefulLayout != null) {
            statefulLayout.showLoading();
        }
    }

    /**
     * ??????????????????
     */
    public void showEmpty() {
        if (statefulLayout != null) {
            statefulLayout.showEmpty();
        }
    }

    /**
     * ??????????????????
     *
     * @param message ???????????????
     */
    public void showEmpty(String message) {
        if (message != null && statefulLayout != null) {
            statefulLayout.showEmpty(message);
        }
    }

    /**
     * ????????????
     *
     * @param clickListener ??????????????????
     */
    public void showError(View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null) {
            statefulLayout.showError(clickListener);
        }
    }

    /**
     * ????????????
     *
     * @param message       ?????????????????????
     * @param clickListener ??????????????????
     */
    public void showError(String message, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null) {
            statefulLayout.showError(message, clickListener);
        }
    }

    /**
     * ????????????
     *
     * @param message       ?????????????????????
     * @param buttonText    ????????????????????????
     * @param clickListener ??????????????????
     */
    public void showError(String message, String buttonText, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null && buttonText != null) {
            statefulLayout.showError(message, buttonText, clickListener);
        }
    }

    /**
     * ??????????????????
     *
     * @param clickListener ??????????????????
     */
    public void showOffline(View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null) {
            statefulLayout.showOffline(clickListener);
        }
    }

    /**
     * ??????????????????
     *
     * @param message       ????????????
     * @param clickListener ????????????
     */
    public void showOffline(String message, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null) {
            statefulLayout.showOffline(message, clickListener);
        }
    }

    /**
     * ??????????????????
     *
     * @param message       ????????????
     * @param buttonText    ??????????????????
     * @param clickListener ????????????
     */
    public void showOffline(String message, String buttonText, View.OnClickListener clickListener) {
        if (statefulLayout != null && clickListener != null && message != null && buttonText != null) {
            statefulLayout.showOffline(message, buttonText, clickListener);
        }
    }

    /**
     * ?????????????????????
     *
     * @param options ???????????????
     */
    public void showCustom(final CustomStateOptions options) {
        if (statefulLayout != null && options != null) {
            statefulLayout.showCustom(options);
        }
    }

    /**
     * ????????????
     */
    protected abstract void listonRefresh(RefreshLayout refreshLayout, RecyclerView recyclerView);

    /**
     * ????????????
     */
    protected abstract void listOnLoadMore(RefreshLayout refreshLayout, RecyclerView recyclerView);

    /**
     * ?????????adapter
     */
    protected abstract BaseAdapter initAdapter(BaseAdapter adapter);

    /**
     * ????????????
     */
    protected abstract void loadData(BaseAdapter adapter);
}
