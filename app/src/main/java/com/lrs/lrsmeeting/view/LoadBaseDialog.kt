package com.lrs.lrsmeeting.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.lrs.lrsmeeting.R
import kotlinx.android.synthetic.main.load_indicator_view.*

/**
 * @description 作用:
 * @date: 2020/4/7
 * @author: 卢融霜
 */
class LoadBaseDialog(context: Context) : Dialog(context, R.style.TransparentDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.load_indicator_view)
    }


    fun setMsg(text: String?) {
        tvLoadName.text = text;
    }
}