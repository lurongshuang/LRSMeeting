package com.lrs.lrsmeeting.activity.main.item

import android.annotation.SuppressLint
import android.app.Person
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lrs.hyrc_base.utils.httputils.CallBackUtil
import com.lrs.hyrc_base.utils.httputils.HyrcHttpUtil
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.login.LoginActivity
import com.lrs.lrsmeeting.activity.main.MainActivity
import com.lrs.lrsmeeting.activity.main.adapter.MeetingAdapter
import com.lrs.lrsmeeting.activity.meeting.CreateMeetingActivity
import com.lrs.lrsmeeting.base.LazyLoadingFragment
import com.lrs.lrsmeeting.bean.MeetingBase
import com.lrs.lrsmeeting.url.Config
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup.OnPopupItemClickListener
import com.xuexiang.xui.widget.statelayout.StatefulLayout
import okhttp3.Call
import org.json.JSONObject
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


/**
 * @description 作用:
 * @date: 2020/4/8
 * @author: 卢融霜
 */
class MeetingFragment : LazyLoadingFragment() {
    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.ll_stateful)
    lateinit var llStateful: StatefulLayout

    @BindView(R.id.refreshLayout)
    lateinit var refreshLayout: SmartRefreshLayout

    private lateinit var adapter: MeetingAdapter;
    private var userId: String? = "";

    companion object {
        private lateinit var fragment: MeetingFragment;
        fun newInstance(): MeetingFragment? {
            val args = Bundle()
            fragment =
                MeetingFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onFirstVisibleToUser(): Boolean {
        return true;
    }

    @SuppressLint("WrongConstant")
    override fun onVisibleToUser() {
        (activity as MainActivity).setTitle(false, "会议", getString(R.string.icontianjia)) {
            val arr = arrayOf("发起会议", "预约会议");
            val mMenuPopup = XUISimplePopup<XUISimplePopup<*>>(context, arr)
                .create(OnPopupItemClickListener { _, item, position ->
                    if (position == 1) {
                        (activity as MainActivity).openAcitivty(CreateMeetingActivity().javaClass);
                    }
                })
            mMenuPopup.showDown(it)
        };
    }

    private fun getData() {
        llStateful.showLoading();


//        var sql = "select * from Meeting where launUserId = '$userId' order by meetingSate"
//        var bmo = BmobQuery<Meeting>();
//        bmo.doSQLQuery(sql, object : SQLQueryListener<Meeting>() {
//            override fun done(t: BmobQueryResult<Meeting>?, e: BmobException?) {
//                if (e != null) {
//                    showToast("查询失败");
//                    llStateful.showError {
//                        getData();
//                    }
//                } else {
//                    if (t != null && t.results.size > 0) {
//                        t.results.forEach {
//                            adapter.addData(it);
//                        }
//                        llStateful.showContent()
//                    } else {
//                        llStateful.showEmpty();
//                    }
//                }
//                refreshLayout.finishRefresh();
//            }
//
//        })


        var mp = hashMapOf<String, String>();
        var json = JSONObject();
//        json.put("userId", SharedPreferencesHelper.getPrefString("userId", null));
//        json.put("orgId", SharedPreferencesHelper.getPrefString("orgId", null));
//        json.put("method", "getRooms");
//        json.put("SID",SharedPreferencesHelper.getPrefString("SID", null));
//        json.put("module", "room");
//            SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
        var c: Calendar = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        val tDa: String = SimpleDateFormat("yyyyMMddHHmmss").format(c.time);
        c.add(Calendar.DAY_OF_MONTH, 3);
        val tmo: String = SimpleDateFormat("yyyyMMddHHmmss").format(c.time);
        json.put("startTime", tDa);
        json.put("endTime", tmo);
        json.put("pageNo", 0);
        json.put("pageSize", 6);
        json.put("method", "list");
        json.put("SID", SharedPreferencesHelper.getPrefString("SID", null));

        mp["params"] = json.toString();
        HyrcHttpUtil.httpPost(Config.ROOM_LIST, mp, object : CallBackUtil<MeetingBase>() {
            override fun onParseResponse(call: Call?, response: okhttp3.Response?): MeetingBase? {
                var jsongBean = response?.body?.string()
                return fromToJson<MeetingBase>(
                    jsongBean,
                    object : TypeToken<MeetingBase?>() {}.type
                );
            }

            override fun onFailure(call: Call?, e: Exception?) {
                llStateful.showError {
                    getData();
                }
            }

            override fun onResponse(response: MeetingBase?) {
                if (response?.result == 0) {
                    if (response?.meetings == null) {
                        openActivity(LoginActivity::class.java);
                        activity?.finish();
                        return;
                    }
                    if (response?.meetings.size > 0) {
                        response.meetings.forEach {
                            adapter.addData(it);
                        }
                        llStateful.showContent()
                    } else {
                        llStateful.showEmpty();
                    }
                } else {
                    llStateful.showEmpty();
                }
                refreshLayout.finishRefresh();
            }
        });
//        var mp1 = hashMapOf<String, String>();
//        var json1 = JSONObject();
//        json1.put("userId", 2);
//        json1.put("orgId", 2);
//        json1.put("method", "getRooms");
//        json1.put("SID", SharedPreferencesHelper.getPrefString("SID", null));
//
//
//        mp["params"] = json1.toString();
//        HyrcHttpUtil.httpPost(
//            "https://demo.527meeting.com/app/room",
//            mp1,
//            object : CallBackUtil.CallBackString() {
//                override fun onFailure(call: Call?, e: java.lang.Exception?) {
//                    Log.e("","");
//                }
//
//                override fun onResponse(response: String?) {
//                    Log.e("","");
//                }
//
//            })
    }

    fun <T> fromToJson(json: String?, listType: Type?): T? {
        var gson = Gson()
        var t: T? = null
        t = gson.fromJson(json, listType)
        return t
    }

    override fun onInvisibleToUser() {

    }

    override fun getLayRes(): Int {

        return R.layout.fragment_meeting
    }

    override fun init(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        userId = SharedPreferencesHelper.getPrefString("userId", "").toString();
        adapter = MeetingAdapter(R.layout.adapter_meeting_item, activity!!);
        val manager = LinearLayoutManager(activity);
        manager.orientation = LinearLayoutManager.VERTICAL;
        recyclerView.layoutManager = manager;
        recyclerView.adapter = adapter;
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        );
        //下拉刷新
        refreshLayout.setOnRefreshListener {
            if (recyclerView.childCount > 0) {
                recyclerView.removeAllViews()
            }
            if (adapter.data.size > 0) {
                adapter.data.clear();
                adapter.clearState();
            }
            getData();
        }

        //加载更多
        refreshLayout.setOnLoadMoreListener {
            refreshLayout.finishLoadMore();
        }

        getData();


    }
}