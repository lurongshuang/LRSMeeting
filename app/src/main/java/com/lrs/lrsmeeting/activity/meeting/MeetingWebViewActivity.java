package com.lrs.lrsmeeting.activity.meeting;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper;
import com.lrs.lrsmeeting.R;
import com.lrs.lrsmeeting.activity.login.LoginActivity;
import com.lrs.lrsmeeting.base.BaseActivity;
import com.lrs.lrsmeeting.url.Config;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;

public class MeetingWebViewActivity extends BaseActivity implements CordovaInterface {
    @BindView(R.id.tutorialView)
    SystemWebView tutorialView;

    //	Session ID 用户登录后产生的身份标识码
    String SID;
    //	用户唯一标识，登录用户传
    int uid;
    //	角色role: 会议场模式：1是主持人 2普通参会人员； 培训模式：1是主持人 2是讲师，3普通参付人员
    int role;
    // 会议房间唯一标识码（可传code和rd两者必传一，code会议ID）
    int rd;
    //会议唯一标识码（可传code和rd两者必传一，code会议ID）
    int code;
    //参会人员昵称;
    String name;
    //会议场模式  1为P2P模式（在p=1或p=2时支持）
    String p;
    //1音频会议室，2网络会议室 3在线培训
    String t;
    //私有部署轻会议控制服务器地址，如124.205.204.242:2443
    String host = "demo.527meeting.com";
    // 流媒体relay服务器地址，如124.205.204.242
    String relayHost = "demo.527meeting.com";

    private long meetingId;
    private String userId;
    private String userName;

    protected CordovaPlugin activityResultCallback = null;
    protected boolean activityResultKeepRunning;
    private CordovaWebView cordovaWebView;

    protected boolean keepRunning = true;
    private BroadcastReceiver receiver;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private boolean isLaunUser;

    private boolean isMeeting = false;

    @Override
    protected int loadView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_meeting_web_view;
    }

    @Override
    protected void initData() {
        meetingId = getIntent().getExtras().getLong("meetingId");
        uid = Integer.parseInt(SharedPreferencesHelper.getPrefString("userId", ""));
        userName = getIntent().getExtras().getString("userName", null);
        if (userName == null) {
            userName = SharedPreferencesHelper.getPrefString("userName", "");
        }
        isLaunUser = getIntent().getExtras().getBoolean("isLaunUser", false);
        SID = SharedPreferencesHelper.getPrefString("SID", "2");
        userId = SharedPreferencesHelper.getPrefString("userId", "2");

        ConfigXmlParser parser = new ConfigXmlParser();
        //这里会解析res/xml/config.xml配置文件
        parser.parse(this);
        //创建一个cordovawebview
        cordovaWebView = new CordovaWebViewImpl(new SystemWebViewEngine(tutorialView));
//        tutorialView.setWebViewClient(new webCli());
        //初始化
        cordovaWebView.init(new CordovaInterfaceImpl(this), parser.getPluginEntries(), parser.getPreferences());
        String webUrl = "file:///android_asset/www/index.html#/service?code=" + meetingId + "&SID=" + SID + "&host=" + host + "&relayHost=" + relayHost + "&name=" + userName + "&uid=" + uid + "&role=" + (isLaunUser ? 1 : 0);
        tutorialView.loadUrl(webUrl);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    final JSONObject data = new JSONObject(intent.getExtras().getString("userdata"));
                    switch (data.getInt("recode")) {
                        case 2001:
                            showToast("被主持人踢出房间");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2002:
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2003:
                            showToast("当前会议还未开始");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2005:
                            showToast("主持人已解散本次会议");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2007:
                            showToast("当前会议链接已失效");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2008:
                            showToast("参会方数已达上限，请联系管理员");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2010:
                            showToast("当前流媒体服务器空间已满，请稍后再试");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2011:
                            showToast("当前会议室人数已达上线");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2012:
                            showToast("会议室已锁定，您将无法参加会议");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 1006:
                            showToast("流媒体服务器异常");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2014:
                            showToast("请稍后再次尝试");
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 1007:
                            showToast("当前网络不稳定");
                            break;
                        case 2000:
                            showToast("账号在其他地点登录，请重新登录");
                            openAcitivty(LoginActivity.class);
                            MeetingWebViewActivity.this.finish();
                            break;
                        case 2004:
                            isMeeting = true;
                            break;


                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("meeting.event"));
    }


    /**
     * native 主动退出会议室，发消息给会议室
     *
     * @param view
     */
    public void startService(View view) {

        final Intent intent = new Intent("527meeting");

        Bundle b = new Bundle();
        b.putString("userdata", "{ data: 'exitRoom'}");
        intent.putExtras(b);
        LocalBroadcastManager.getInstance(this).sendBroadcastSync(intent);
    }

    @Override
    public void startActivityForResult(CordovaPlugin plugin, Intent intent, int i) {
        this.activityResultCallback = plugin;
        this.activityResultKeepRunning = this.keepRunning;

        // If multitasking turned on, then disable it for activities that return results
        if (plugin != null) {
            this.keepRunning = false;
        }

        // Start activity
        super.startActivityForResult(intent, i);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // If back key
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isMeeting) {
                Log.d("KeyEvent", "KeyUp has been triggered on the view KEYCODE_BACK" + keyCode);
                tutorialView.loadUrl("javascript:cordova.fireDocumentEvent('backbutton');");
                return true;
            } else {
                this.finish();
            }
        }
        // Legacy
        else if (keyCode == KeyEvent.KEYCODE_MENU) {
            tutorialView.loadUrl("javascript:cordova.fireDocumentEvent('menubutton');");
            return true;
        }
        // If search key
        else if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            tutorialView.loadUrl("javascript:cordova.fireDocumentEvent('searchbutton');");
            return true;
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
        cordovaWebView.handleDestroy();
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {
        this.activityResultCallback = plugin;
    }

    @Override
    protected void clearData() {

    }


    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public Object onMessage(String s, Object o) {
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    @Override
    public void requestPermission(CordovaPlugin cordovaPlugin, int i, String s) {

    }

    @Override
    public void requestPermissions(CordovaPlugin cordovaPlugin, int i, String[] strings) {

    }

    @Override
    public boolean hasPermission(String s) {
        return true;
    }

    public class webCli extends SystemWebViewClient {
        public webCli(SystemWebViewEngine parentEngine) {
            super(parentEngine);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }
}
