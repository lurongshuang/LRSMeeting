package com.lrs.lrsmeeting.activity.main.item

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.lrs.hyrc_base.utils.sharedpreferences.SharedPreferencesHelper
import com.lrs.lrsmeeting.R
import com.lrs.lrsmeeting.activity.about.AboutActivity
import com.lrs.lrsmeeting.activity.login.LoginActivity
import com.lrs.lrsmeeting.activity.setting.SetingActivity
import com.lrs.lrsmeeting.base.LazyLoadingFragment
import com.lrs.lrsmeeting.url.Config
import com.xuexiang.xui.widget.alpha.XUIAlphaLinearLayout
import com.xuexiang.xui.widget.imageview.RadiusImageView
import java.util.*

/**
 * @description 作用:
 * @date: 2020/4/8
 * @author: 卢融霜
 */
class MyselfFragment : LazyLoadingFragment(), View.OnClickListener {
    @BindView(R.id.rivHead)
    lateinit var rivHead: RadiusImageView;

    @BindView(R.id.tvHead)
    lateinit var tvHead: TextView;

    @BindView(R.id.xuiLLkf)
    lateinit var xuiLLkf: XUIAlphaLinearLayout

    @BindView(R.id.xuiLLabout)
    lateinit var xuiLLabout: XUIAlphaLinearLayout

    @BindView(R.id.xuiSeting)
    lateinit var xuiSeting: XUIAlphaLinearLayout

    companion object {
        private lateinit var fragment: MyselfFragment;
        fun newInstance(): MyselfFragment? {
            val args = Bundle()
            fragment =
                MyselfFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun init(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        //初始化 头像 信息
        var userId = SharedPreferencesHelper.getPrefString("userId", "");
        if (userId.isNotEmpty()) {
            tvHead.text = SharedPreferencesHelper.getPrefString("userName", "登录/注册");
            var userUrl = SharedPreferencesHelper.getPrefString("userURL", "");
            if (userUrl.isNotEmpty()) {
                userUrl = Config.HOST + userUrl.substring(1);
            }
            Glide.with(activity!!).load(userUrl).into(rivHead);
        } else {
            tvHead.text = "登录/注册";
            Glide.with(activity!!).load(resources.getDrawable(R.drawable.ic_head_def))
                .into(rivHead);
        }
        xuiLLkf.setOnClickListener(this);
        xuiLLabout.setOnClickListener(this);
        xuiSeting.setOnClickListener(this);
    }


    override fun onFirstVisibleToUser(): Boolean {
        return false;
    }

    override fun onInvisibleToUser() {

    }

    override fun getLayRes(): Int {
        return R.layout.fragment_myself;
    }

    override fun onVisibleToUser() {
//        setTitle(false, "我的");
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.xuiLLkf -> {
                val phoneNumber = "010-80360190"
                val dialIntent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(dialIntent)
            }
            R.id.xuiLLabout -> {
                openActivity(AboutActivity::class.java)
            }
            R.id.xuiSeting -> {
                openActivity(SetingActivity().javaClass)
            }
        }
    }

}