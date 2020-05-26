package com.lrs.lrsmeeting.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.lrs.hyrc_base.activity.splash.SplashActivity
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.view.FontIconView

/**
 * @description 作用:
 * @date: 2020/4/8
 * @author: 卢融霜
 */
abstract class LazyLoadingFragment : Fragment() {
    private var isCanLoading = false
    private var isFirstVisibleToUser = false
    private var isVisibleToUser = false
    var rootView: View? = null;

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isFirstVisibleToUser = true;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val intent = Intent(activity, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        try {
            super.onCreate(savedInstanceState)
        } catch (e: Exception) {
        }
        isCanLoading = false
        isFirstVisibleToUser = true
        isVisibleToUser = false
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        onVisibleChanged(isVisibleToUser)
    }

    private fun onVisibleChanged(isVisibleToUser: Boolean) {
        if (isCanLoading) {
            if (userVisibleHint) {
                isFirstVisibleToUser = if (isFirstVisibleToUser) {
                    if (onFirstVisibleToUser()) {
                        onVisibleToUser()
                    } else {
                        onVisibleToUser()
                    }
                    false
                } else {
                    onVisibleToUser()
                    false
                }
            } else {
                onInvisibleToUser()
            }
        }
    }

    /**
     * 第一次对用户可见
     *
     * @return 第一次可见任然执行 [LazyLoadingFragment.onVisibleChanged]
     */
    protected abstract fun onFirstVisibleToUser(): Boolean

    /**
     * 对用户可见
     */
    protected abstract fun onVisibleToUser()

    /**
     * 对用户不可见
     */
    protected abstract fun onInvisibleToUser()

    protected abstract fun getLayRes(): Int

    protected abstract fun init(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )

    var unbinder: Unbinder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayRes(), container, false)
            unbinder = ButterKnife.bind(this, rootView!!);
            init(inflater, container, savedInstanceState)
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCanLoading = true
        onVisibleChanged(userVisibleHint)
    }

    protected fun isFirstVisibleToUser(): Boolean {
        return isFirstVisibleToUser
    }

    protected fun isCanLoading(): Boolean {
        return isCanLoading
    }

    fun openActivity(cls: Class<*>?) {
        openActivity(cls, null)
    }

    fun openActivity(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(activity, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 提示信息  吐司
     */
    fun showToast(text: String?) {
        if (activity != null) {
            Toast.makeText(activity!!.applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 提示信息  吐司
     */
    fun showToast(id: Int) {
        if (activity != null) {
            Toast.makeText(activity!!.applicationContext, id, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 设置状态栏
     *
     * @param isShowLeftRes 是否显示 左侧按钮
     * @param name          标题
     */
    fun setTitle(isShowLeftRes: Boolean, name: String?) {
        val textView = rootView?.findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView? = rootView?.findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.visibility = if (isShowLeftRes) View.VISIBLE else View.GONE
            fontIconView.setOnClickListener(View.OnClickListener { activity?.finish() })
        }
        val tv_rightText: FontIconView? = rootView?.findViewById(R.id.tv_rightText)
        if (tv_rightText != null) {
            tv_rightText.visibility = View.GONE
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
        val textView = rootView?.findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView? = rootView?.findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.setVisibility(if (isShowLeftRes) View.VISIBLE else View.GONE)
            fontIconView.setOnClickListener(View.OnClickListener { activity?.finish() })
        }
        val tv_rightText: FontIconView? = rootView?.findViewById(R.id.tv_rightText)
        if (tv_rightText != null) {
            tv_rightText.visibility = View.VISIBLE
            tv_rightText.text = right
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
        val textView = rootView?.findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView? = rootView?.findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.visibility = if (isShowLeftRes) View.VISIBLE else View.GONE
            fontIconView.setOnClickListener(View.OnClickListener { activity?.finish() })
        }
        val tv_rightText: FontIconView? = rootView?.findViewById(R.id.tv_rightText)
        if (tv_rightText != null) {
            tv_rightText.visibility = View.VISIBLE
            tv_rightText.text = right
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
        val textView = rootView?.findViewById<TextView>(R.id.tv_title)
        if (textView != null) {
            textView.text = name
        }
        val fontIconView: FontIconView? = rootView?.findViewById(R.id.iv_leftIcon)
        if (fontIconView != null) {
            fontIconView.setVisibility(if (isShowLeftRes) View.VISIBLE else View.GONE)
            fontIconView.setOnClickListener(View.OnClickListener { activity?.finish() })
        }
        if (right != null && rightOnclick != null) {
            val tv_rightText: FontIconView? = rootView?.findViewById(R.id.tv_rightText)
            if (tv_rightText != null) {
                tv_rightText.visibility = View.VISIBLE
                tv_rightText.setText(right)
                tv_rightText.setOnClickListener(rightOnclick)
            }
        }
        if (rightTitle != null && rightViewOnclick != null) {
            val tv_rightTitle = rootView?.findViewById<TextView>(R.id.tv_rightTextTitle)
            if (tv_rightTitle != null) {
                tv_rightTitle.visibility = View.VISIBLE
                tv_rightTitle.text = rightTitle
                tv_rightTitle.setOnClickListener(rightViewOnclick)
            }
        }
    }

}