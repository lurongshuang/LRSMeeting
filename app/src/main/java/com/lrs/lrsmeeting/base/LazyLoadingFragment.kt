package com.lrs.lrsmeeting.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.lrs.hyrc_base.activity.splash.SplashActivity

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
}