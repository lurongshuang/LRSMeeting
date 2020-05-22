package com.lrs.lrsmeeting.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import butterknife.ButterKnife
import com.gyf.barlibrary.ImmersionBar
import com.lrs.hyrc_base.activity.splash.SplashActivity
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.view.FontIconView
import com.lrs.lrsmeeting.view.LoadBaseDialog
import com.xuexiang.xui.widget.statelayout.CustomStateOptions
import com.xuexiang.xui.widget.statelayout.StatefulLayout

/**
 * @description 作用:
 * @date: 2020/4/7
 * @author: 卢融霜
 */
abstract class BaseActivity : AppCompatActivity() {
    var permissionCallBack: onPermissionCallBack? = null

    //权限集合
    var permission = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var loadBaseDialog: LoadBaseDialog? = null
    var mybro: myBroadCast? = null;

    private val BId = "com.lrs.meeting"

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    open fun setView() {
        setContentView(loadView())
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        setView();
        ButterKnife.bind(this)
        loadBaseDialog = LoadBaseDialog(this)
        if (getPermossion()) {
            initData()
            //退出监听
            mybro = myBroadCast()
            val intentFilter = IntentFilter(BId)
            registerReceiver(mybro, intentFilter)


//            ImmersionBar.with(this)
//                .statusBarDarkFont(false, 1.0f)
//                .statusBarColorTransformEnable(false)
//                .statusBarColor(resources.getColor(R.attr.colorPrimary))
//                .fitsSystemWindows(true)
//                .init();
        }
    }

    /**
     * 获取权限
     */
    open fun getPermossion(): Boolean {
        return true
    }

    override fun onDestroy() {
        ImmersionBar.with(this).destroy()
        clearData()
        if (mybro != null) {
            unregisterReceiver(mybro)
        }
        super.onDestroy()
    }

    fun exitApp() {
        val intent = Intent(BId)
        intent.putExtra("close", 1)
        sendBroadcast(intent)
    }

    /**
     * 设置布局
     *
     * @return 布局id
     */
    protected abstract fun loadView(): Int

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 清楚数据
     */
    protected abstract fun clearData()

    /**
     * 提示信息  吐司
     */
    fun showToast(text: String?) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    /**
     * 提示信息  吐司
     */
    fun showToast(text: String?, type: Int) {
        Toast.makeText(applicationContext, text, type).show()
    }

    /**
     * 提示信息  吐司
     */
    fun showToast(id: Int) {
        Toast.makeText(applicationContext, id, Toast.LENGTH_SHORT).show()
    }


    /**
     * 跳转activity
     */
    fun openAcitivty(activity: Class<*>?) {
        openAcitivty(activity, null)
    }

    /**
     * 跳转activity
     */
    fun openAcitivty(activity: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this@BaseActivity, activity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    //申请回调
    interface onPermissionCallBack {
        //获取权限成功
        fun onSuccess()

        //禁止
        fun choiceProhibit()

        //禁止不再询问
        fun choiceProhibitNotAsking()
    }

    /**
     * 使用默认权限集合
     */
    fun requetPermission(permissionCallBack: onPermissionCallBack?) {
        this.permissionCallBack = permissionCallBack
        if (ContextCompat.checkSelfPermission(
                this@BaseActivity,
                permission.toString()
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this@BaseActivity, permission, 1)
        } else {
            //权限全部通过
            this.permissionCallBack!!.onSuccess()
        }
    }


    /**
     * 使用自定义权限集合  或者验证指定权限是够通过
     */
    fun requetPermission(
        perm: Array<String?>?,
        permissionCallBack: onPermissionCallBack?
    ) {
        this.permissionCallBack = permissionCallBack
        if (ContextCompat.checkSelfPermission(
                this@BaseActivity,
                perm.toString()
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this@BaseActivity, perm!!, 1)
        } else {
            //权限全部通过
            this.permissionCallBack!!.onSuccess()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //选择了“始终允许”
                    if (i == permissions.size - 1) {
                        permissionCallBack!!.onSuccess()
                    }
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this@BaseActivity,
                            permissions[i]!!
                        )
                    ) {
                        //用户选择了禁止不再询问
                        permissionCallBack!!.choiceProhibitNotAsking()
                    } else {
                        //选择禁止
                        permissionCallBack!!.choiceProhibit()
                    }
                    break;
                }
            }
        }
    }

    /**
     * 设置状态栏
     *
     * @param isShowLeftRes 是否显示 左侧按钮
     * @param name          标题
     */
    fun setTitle(isShowLeftRes: Boolean, name: String?) {
        val textView = findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView = findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.setVisibility(if (isShowLeftRes) View.VISIBLE else View.GONE)
            fontIconView.setOnClickListener(View.OnClickListener { finish() })
        }
        val tv_rightText: FontIconView = findViewById(R.id.tv_rightText)
        if (tv_rightText != null) {
            tv_rightText.setVisibility(View.GONE)
        }
    }

    /**
     * 设置状态栏
     *
     * @param isShowLeftRes 是否显示 左侧按钮
     * @param name          标题
     */
    fun setTitle(
        isShowLeftRes: Boolean,
        name: String?,
        right: String?
    ) {
        val textView = findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView = findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.setVisibility(if (isShowLeftRes) View.VISIBLE else View.GONE)
            fontIconView.setOnClickListener(View.OnClickListener { finish() })
        }
        val tv_rightText: FontIconView = findViewById(R.id.tv_rightText)
        if (tv_rightText != null) {
            tv_rightText.setVisibility(View.VISIBLE)
            tv_rightText.setText(right)
        }
    }

    /**
     * 设置状态栏
     *
     * @param isShowLeftRes 是否显示 左侧按钮
     * @param name          标题
     */
    fun setTitle(
        isShowLeftRes: Boolean,
        name: String?,
        right: String?,
        rightOnclick: View.OnClickListener?
    ) {
        val textView = findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView = findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.setVisibility(if (isShowLeftRes) View.VISIBLE else View.GONE)
            fontIconView.setOnClickListener(View.OnClickListener { finish() })
        }
        val tv_rightText: FontIconView = findViewById(R.id.tv_rightText)
        if (tv_rightText != null) {
            tv_rightText.setVisibility(View.VISIBLE)
            tv_rightText.setText(right)
            tv_rightText.setOnClickListener(rightOnclick)
        }
    }

    /**
     * 设置状态栏
     *
     * @param isShowLeftRes 是否显示 左侧按钮
     * @param name          标题
     */
    fun setTitle(
        isShowLeftRes: Boolean,
        name: String?,
        right: String?,
        rightOnclick: View.OnClickListener?,
        rightTitle: String?,
        rightViewOnclick: View.OnClickListener?
    ) {
        val textView = findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView = findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.setVisibility(if (isShowLeftRes) View.VISIBLE else View.GONE)
            fontIconView.setOnClickListener(View.OnClickListener { finish() })
        }
        if (right != null && rightOnclick != null) {
            val tv_rightText: FontIconView = findViewById(R.id.tv_rightText)
            if (tv_rightText != null) {
                tv_rightText.setVisibility(View.VISIBLE)
                tv_rightText.setText(right)
                tv_rightText.setOnClickListener(rightOnclick)
            }
        }
        if (rightTitle != null && rightViewOnclick != null) {
            val tv_rightTitle = findViewById<TextView>(R.id.tv_rightTextTitle)
            if (tv_rightTitle != null) {
                tv_rightTitle.visibility = View.VISIBLE
                tv_rightTitle.text = rightTitle
                tv_rightTitle.setOnClickListener(rightViewOnclick)
            }
        }
    }

    interface SearchOnclickListener {
        fun OnSearch(text: String?)
    }


    fun showKeyboard(editText: EditText) {
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        this.window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    fun hideKeyboard(view: View) {
        val imm = view.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 显示内容
     */
    fun showContent(id: Int) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null) {
            statefulLayout.showContent()
        }
    }

    /**
     * 显示加载中
     *
     * @param message 提示信息
     */
    fun showLoading(id: Int, message: String?) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (message != null && statefulLayout != null) {
            statefulLayout.showLoading(message)
        }
    }

    /**
     * 显示加载中
     */
    fun showLoading(id: Int) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null) {
            statefulLayout.showLoading()
        }
    }

    /**
     * 显示暂无数据
     */
    fun showEmpty(id: Int) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null) {
            statefulLayout.showEmpty()
        }
    }

    /**
     * 显示暂无数据
     *
     * @param message 自定义信息
     */
    fun showEmpty(id: Int, message: String?) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (message != null && statefulLayout != null) {
            statefulLayout.showEmpty(message)
        }
    }

    /**
     * 显示错误
     *
     * @param clickListener 点击重试按钮
     */
    fun showError(id: Int, clickListener: View.OnClickListener?) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null && clickListener != null) {
            statefulLayout.showError(clickListener)
        }
    }

    /**
     * 显示错误
     *
     * @param message       自定义提示信息
     * @param clickListener 点击重试按钮
     */
    fun showError(
        id: Int,
        message: String?,
        clickListener: View.OnClickListener?
    ) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null && clickListener != null && message != null) {
            statefulLayout.showError(message, clickListener)
        }
    }

    /**
     * 显示错误
     *
     * @param message       自定义提示信息
     * @param buttonText    重试按钮提示信息
     * @param clickListener 点击按钮事件
     */
    fun showError(
        id: Int,
        message: String?,
        buttonText: String?,
        clickListener: View.OnClickListener?
    ) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null && clickListener != null && message != null && buttonText != null) {
            statefulLayout.showError(message, buttonText, clickListener)
        }
    }

    /**
     * 显示网络离线
     *
     * @param clickListener 点击按钮事件
     */
    fun showOffline(id: Int, clickListener: View.OnClickListener?) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null && clickListener != null) {
            statefulLayout.showOffline(clickListener)
        }
    }

    /**
     * 显示网络离线
     *
     * @param message       提示消息
     * @param clickListener 点击事件
     */
    fun showOffline(
        id: Int,
        message: String?,
        clickListener: View.OnClickListener?
    ) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null && clickListener != null && message != null) {
            statefulLayout.showOffline(message, clickListener)
        }
    }

    /**
     * 显示网络离线
     *
     * @param message       提示消息
     * @param buttonText    按钮显示信息
     * @param clickListener 点击事件
     */
    fun showOffline(
        id: Int,
        message: String?,
        buttonText: String?,
        clickListener: View.OnClickListener?
    ) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null && clickListener != null && message != null && buttonText != null) {
            statefulLayout.showOffline(message, buttonText, clickListener)
        }
    }

    /**
     * 显示自定义布局
     *
     * @param options 自定义布局
     */
    fun showCustom(id: Int, options: CustomStateOptions?) {
        val statefulLayout: StatefulLayout = findViewById(id)
        if (statefulLayout != null && options != null) {
            statefulLayout.showCustom(options)
        }
    }

    inner class myBroadCast : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val close = intent.getIntExtra("close", 0)
            if (close == 1) {
                finish()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}