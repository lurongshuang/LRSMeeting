package com.lrs.lrsmeeting.activity.meeting

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobQueryResult
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SQLQueryListener
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.login.LoginActivity
import com.lrs.lrsmeeting.activity.meeting.adapter.SelectAdapter
import com.lrs.lrsmeeting.activity.meeting.onInterface.OnAdapterOnclick
import com.lrs.lrsmeeting.base.BaseActivity
import com.lrs.lrsmeeting.bean.EventBusMeetingListBean
import com.lrs.lrsmeeting.bean.User
import com.xuexiang.xui.widget.statelayout.StatefulLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class SelectActivity : BaseActivity(), OnAdapterOnclick {
    @BindView(R.id.rvlist)
    lateinit var rvlist: RecyclerView;

    @BindView(R.id.tvSelectNum)
    lateinit var tvSelectNum: TextView;

    @BindView(R.id.tvOk)
    lateinit var tvOk: TextView;

    @BindView(R.id.sfLayout)
    lateinit var sfLayout: StatefulLayout;
    private lateinit var adapter: SelectAdapter;
    private var userId: String? = null

    override fun loadView(): Int {
        return R.layout.activity_select
    }

    private var tagIT = 200;

    @Subscribe
    fun eventSbusMessage(eventBusMeetingListBean: EventBusMeetingListBean) {
        when (eventBusMeetingListBean.id) {
        }
    }

    override fun initData() {
        setTitle(true, "选择联系人");
        userId = SharedPreferencesHelper.getPrefString(  "userId", null)
        if (userId == null) {
            openAcitivty(LoginActivity::class.java)
            finish()
        }
        //注册监听
        EventBus.getDefault().register(this);

        adapter = SelectAdapter(R.layout.item_select, this)
        adapter.setUserId(userId)
        val manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.VERTICAL
        rvlist!!.layoutManager = manager
        //添加Android自带的分割线
        rvlist!!.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        rvlist!!.adapter = adapter
        adapter.setAdapterOnclick(this);
        //获取 已选用户组
        var list = intent.extras?.getStringArrayList("meetingList");
        if (list != null && list.size > 0) {
            list.forEach {
                adapter.getMap()?.put(it, 1);
            }
        }
        getData()
        tvOk!!.setOnClickListener {
            if (adapter.getSelectNum() > 0) {
                val build = Bundle()
                build.putStringArrayList(
                    "selectUser",
                    adapter.getSelectUser() as ArrayList<String?>
                )
                var messageIt = EventBusMeetingListBean(
                    200,
                    adapter.getSelectUserOfUser() as MutableList<User>
                )
                EventBus.getDefault().post(messageIt);
                finish()
            }
        }
    }

    private lateinit var list: MutableList<User>;

    /**
     * 获取数据库数据
     */
    private fun getData() {
        sfLayout.showLoading();
        val bmobQuery = BmobQuery<User>();
        val sql = "select * from User";
        bmobQuery.doSQLQuery(sql, object : SQLQueryListener<User>() {
            override fun done(t: BmobQueryResult<User>?, e: BmobException?) {
                if (t != null && t.results.size > 0) {
                    list = ArrayList<User>();
                    if (list.size > 0) {
                        list.clear()
                    }
                    list = t.results;
                    adapter.addData(list);
                    sfLayout.showContent();
                } else {
                    sfLayout.showEmpty();
                }
            }

        })
    }

    override fun clearData() {
        EventBus.getDefault().unregister(this);
    }

    override fun onClickListener(position: Int, state: Boolean) {
        Log.e("", "")
        val num: Int = adapter.getSelectNum()
        if (num > 0) {
            tvSelectNum!!.setTextColor(resources.getColor(R.color.colorPrimary))
            tvOk!!.setTextColor(resources.getColor(R.color.colorPrimary))
        } else {
            tvSelectNum!!.setTextColor(resources.getColor(R.color.color_gray))
            tvOk!!.setTextColor(resources.getColor(R.color.color_gray))
        }
        tvSelectNum!!.text = "已选择" + num + "人"
    }
}
