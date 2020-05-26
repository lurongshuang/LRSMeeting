package com.lrs.lrsmeeting.activity.main.item

import android.annotation.SuppressLint
import android.app.Person
import android.content.Intent
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
import com.lrs.lrsmeeting.activity.meeting.CodeOpenActivity
import com.lrs.lrsmeeting.activity.meeting.CreateMeetingActivity
import com.lrs.lrsmeeting.base.BaseActivity
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

    private var pageNum = 0;
    private var pageSize = 10;

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
//         setTitle(false, "会议", getString(R.string.icontianjia)) {
//            val arr = arrayOf("发起会议", "预约会议");
//            val mMenuPopup = XUISimplePopup<XUISimplePopup<*>>(context, arr)
//                .create(OnPopupItemClickListener { _, item, position ->
//                    if (position == 1) {
//                        (activity as MainActivity).openAcitivty(CreateMeetingActivity().javaClass);
//                    }
//                })
//            mMenuPopup.showDown(it)
//        };
        setTitle(false, "会议", getString(R.string.icontianjia)) {
            val arr = arrayOf("邀请码");
            val mMenuPopup = XUISimplePopup<XUISimplePopup<*>>(context, arr)
                .create(OnPopupItemClickListener { _, item, position ->
                    if (position == 0) {
                        (activity as MainActivity).openAcitivty(CodeOpenActivity().javaClass);
                    }
                })
            mMenuPopup.showDown(it)
        };
    }


    private fun getData() {
        if (pageNum == 0) {
            llStateful.showLoading();
        }

        var mp = hashMapOf<String, String>();
        var json = JSONObject();
        var c: Calendar = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -30);
        val tDa: String = SimpleDateFormat("yyyyMMddHHmmss").format(c.time);
        c.add(Calendar.DAY_OF_MONTH, 30);
        val tmo: String = SimpleDateFormat("yyyyMMddHHmmss").format(c.time);
        json.put("startTime", tDa);
        json.put("endTime", tmo);
        json.put("pageNo", pageNum);
        json.put("pageSize", pageSize);
        json.put("method", "listByMonth");
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
                        if (pageNum == 0) {
                            llStateful.showEmpty("最近30天暂无会议");
                        }
                        showToast("没有更多数据");
                        refreshLayout.finishLoadMore();
                        refreshLayout.setEnableLoadMore(false);
                        return;
                    }

                    refreshLayout.setEnableLoadMore(true);
                    if (response?.meetings.size > 0) {
                        response.meetings.forEach {
                            adapter.addData(it);
                        }
                        llStateful.showContent()
                    } else {
                        llStateful.showEmpty("最近30天暂无会议");
                    }
                } else {
                    llStateful.showEmpty("最近30天暂无会议");
                }
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    private fun autoLogin() {
        var userPhone = SharedPreferencesHelper.getPrefString("userPhone", "");
        var passWord = SharedPreferencesHelper.getPrefString("userMd5PWd", "");
        var mapPm = hashMapOf<String, String>();
        var jData = JSONObject();
        jData.put("name", userPhone);
        jData.put("password", passWord);
        jData.put("method", "login");
        jData.put("SID", "");
        jData.put("url", "");
        jData.put("token", "");
        jData.put("company", "北京宏宇睿晨技术有限公司");
        jData.put("appkey", "F6JJKH7LKJKJ8JKJH6GTYMBPO9");

        mapPm["params"] = jData.toString();
        HyrcHttpUtil.httpPost(Config.CURRENCY, mapPm, object : CallBackUtil.CallBackString() {
            override fun onFailure(call: Call?, e: java.lang.Exception?) {
            }

            override fun onResponse(response: String?) {
                var obj = JSONObject(response);
                if (obj.getInt("result") == 0) {
                    var userIt = obj.getJSONObject("user");
                    SharedPreferencesHelper.setPrefString(
                        "orgId",
                        (obj.getJSONArray("organisations").get(0) as JSONObject).getInt("id")
                            .toString()
                    );
                    SharedPreferencesHelper.setPrefString(
                        "userId",
                        userIt.getInt("id").toString()
                    );
                    SharedPreferencesHelper.setPrefString(
                        "userName",
                        userIt.getString("name")
                    );
                    SharedPreferencesHelper.setPrefString(
                        "userURL",
                        userIt.getString("pictureurl")
                    );
                    SharedPreferencesHelper.setPrefString(
                        "SID",
                        obj.getString("SID")
                    );
                    getData();
                } else {
                    openActivity(LoginActivity().javaClass)
                }
            }

        })
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
            pageNum = 0;
//            getData();
            autoLogin();
        }

        //加载更多
        refreshLayout.setOnLoadMoreListener {
            pageNum += 1;
            getData();
        }

        //自动登录
        autoLogin();


    }
}