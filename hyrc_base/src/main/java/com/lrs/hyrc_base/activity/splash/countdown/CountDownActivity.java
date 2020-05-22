package com.lrs.hyrc_base.activity.splash.countdown;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lrs.hyrc_base.R;
import com.lrs.hyrc_base.R2;
import com.lrs.hyrc_base.activity.base.HyrcBaseActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class CountDownActivity extends HyrcBaseActivity implements View.OnClickListener {
    @BindView(R2.id.tvClose)
    TextView tvClose;

    private static final int NOT_NOTICE = 2;
    private AlertDialog alertDialog;
    private AlertDialog mDialog;
    CountDownTimer timer;
    public final int GOTOMESSAGE = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == GOTOMESSAGE) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
            }
        }
    };


    @Override
    protected int loadView() {
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏顶部状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //在你的activity的onCreate方法下添加下列代码（如果是自定义的BaseActivity的话，就在你自定义的Activity下添加，这样的话只要其他的继承你的BaseActivity，只需要改一次就可以）
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        return R.layout.activity_count_down;
    }

    @Override
    protected void initFindViewById() {

    }

    @Override
    protected void initView() {

    }

    private void beginCountDown() {
        if (timer == null) {
            timer = new CountDownTimer(3 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //millisUntilFinished / 1000
                    int endTime = (int) ((millisUntilFinished / 1000) + 1);
                    tvClose.setText("跳过 " + endTime);
                }

                @Override
                public void onFinish() {
                    goToNext();
                }
            };
            timer.start();
        }
    }

    @Override
    protected void initData() {
    }

    private void goToNext() {
        //隐式跳转
        Message message = new Message();
        message.what = GOTOMESSAGE;
        handler.sendMessage(message);
        Intent intent = new Intent("com.hyrc.HyrcMainActivity");
        intent.setData(Uri.parse("hyrc_meeting://hyrc.com:8888"));
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvClose) {
            goToNext();
        }
    }

    @Override
    public boolean getPermossion() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //小于6
            return true;
        }
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        tvClose.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, String.valueOf(permission)) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission, 1);
        } else {
            beginCountDown();
        }
        requetPermission(new OnPermissionCallBack() {
            @Override
            public void onSuccess() {
                beginCountDown();
            }

            @Override
            public void choiceProhibit() {
                //选择禁止
                if (alertDialog != null && alertDialog.isShowing()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(CountDownActivity.this);
                builder.setTitle(R.string.perTitle)
                        .setMessage(R.string.perText)
                        .setPositiveButton("去允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (alertDialog != null && alertDialog.isShowing()) {
                                    alertDialog.dismiss();
                                }
                                ActivityCompat.requestPermissions(CountDownActivity.this,
                                        permission, 1);
                            }
                        });
                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }

            @Override
            public void choiceProhibitNotAsking() {
                if (alertDialog != null && alertDialog.isShowing()) {
                    return;
                }
                //用户选择了禁止不再询问
                AlertDialog.Builder builder = new AlertDialog.Builder(CountDownActivity.this);
                builder.setTitle(R.string.perTitle)
                        .setMessage(R.string.perText)
                        .setPositiveButton("去允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (mDialog != null && mDialog.isShowing()) {
                                    mDialog.dismiss();
                                }
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, NOT_NOTICE);
                            }
                        });
                mDialog = builder.create();
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
            }
        });
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOT_NOTICE) {
            //由于不知道是否选择了允许所以需要再次判断
            getPermossion();
        }
    }
}
