package com.lrs.lrsmeeting.activity.login.model

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobQueryResult
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SQLQueryListener
import com.google.gson.JsonObject
import com.lrs.hyrc_base.utils.httputils.CallBackUtil
import com.lrs.hyrc_base.utils.httputils.HyrcHttpUtil
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper
import com.lrs.hyrc_base.utils.thread.ThreadUtils
import com.lrs.lrsmeeting.activity.main.MainActivity
import com.lrs.lrsmeeting.activity.register.RegisterActivity
import com.lrs.lrsmeeting.base.BaseActivity
import com.lrs.lrsmeeting.bean.User
import com.lrs.lrsmeeting.url.Config
import com.lrs.lrsmeeting.view.LoadBaseDialog
import okhttp3.Call
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import io.reactivex.functions.Consumer as Consumer1


/**
 * @description 作用:
 * @date: 2020/3/24
 * @author: 卢融霜
 */
class LoginModel(content: Activity) : BaseObservable() {

    private var content: Activity = content;

    /**
     * 手机号
     */
    val userPhone: ObservableField<String> = ObservableField();

    /**
     * 密码
     */
    val passWord: ObservableField<String> = ObservableField();

    /**
     * 登录
     */
    fun onLogin(v: View) {
        if (userPhone.get() == null || userPhone.get().toString().isEmpty()) {
            toastString("请输入账号");
            return;
        }
        if (passWord.get() == null || passWord.get().toString().isEmpty()) {
            toastString("请输入密码");
            return;
        }
        (content as BaseActivity).loadBaseDialog?.show();
//        val bmobQuery = BmobQuery<User>();
//        val sql =
//            "select * from User where userPhone = '" + userPhone.get() + "' and passWord = '" + passWord.get() + "'";
//        bmobQuery.doSQLQuery(sql, object : SQLQueryListener<User>() {
//            override fun done(t: BmobQueryResult<User>?, e: BmobException?) {
//                loadBaseDialog.dismiss();
//                if (t != null && t.results.size > 0) {
//                    var user: User = t.results[0];
//                    toastString("登录成功：欢迎您：" + user.userName);
//                    SharedPreferencesHelper.setPrefString(content, "userId", userPhone.get());
//                    SharedPreferencesHelper.setPrefString(content, "userName", user.userName);
//                    SharedPreferencesHelper.setPrefString(content, "userURL", user.userURL);
//                    val intent = Intent(content, MainActivity().javaClass)
////                    intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, userPhone.get())
//                    content.startActivity(intent);
//                    val task: TimerTask = object : TimerTask() {
//                        override fun run() {
//                            content.finish();
//                        }
//                    }
//                    val timer = Timer()
//                    timer.schedule(task, 1 * 1000)
//                } else {
//                    toastString("用户名密码错误，请重试");
//                }
//            }
//        });

        var mapPm = hashMapOf<String, String>();
        var jData = JSONObject();
        jData.put("name", userPhone.get().toString());
        jData.put("password", passWord.get().toString());
        jData.put("method", "login");
        jData.put("SID", "");
        jData.put("url", "");
        jData.put("token", "");
        jData.put("company", "北京宏宇睿晨技术有限公司");
        jData.put("appkey", "F6JJKH7LKJKJ8JKJH6GTYMBPO9");

        mapPm["params"] = jData.toString();
        HyrcHttpUtil.httpPost(Config.CURRENCY, mapPm, object : CallBackUtil.CallBackString() {
            override fun onFailure(call: Call?, e: Exception?) {
                toastString("服务器异常，请重试");
                (content as BaseActivity).loadBaseDialog?.dismiss();
            }

            override fun onResponse(response: String?) {
                var obj = JSONObject(response);
                (content as BaseActivity).loadBaseDialog?.dismiss();
                if (obj.getInt("result") == 0) {
                    var userIt = obj.getJSONObject("user");
                    toastString("登录成功");
                    SharedPreferencesHelper.setPrefString("userPhone", userPhone.get());
                    SharedPreferencesHelper.setPrefString(
                        "orgId",
                        (obj.getJSONArray("organisations").get(0) as JSONObject).getString("id")
                    );
                    SharedPreferencesHelper.setPrefString(
                        "userId",
                        userIt.getString("id")
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
                    val intent = Intent(content, MainActivity().javaClass)
                    content.startActivity(intent);
                    val task: TimerTask = object : TimerTask() {
                        override fun run() {
                            content.finish();
                        }
                    }
                    val timer = Timer()
                    timer.schedule(task, 1 * 1000)
                } else {
                    toastString("用户名密码错误，请重试");
                }
            }

        })
    }

    /**
     * 去注册
     */
    fun onToRegister(v: View) {
        val intent = Intent(content, RegisterActivity().javaClass);
        content.startActivity(intent)
    }

    /**
     * toast
     */
    fun toastString(text: String) {
        Toast.makeText(content, text, Toast.LENGTH_SHORT).show();
    }


}