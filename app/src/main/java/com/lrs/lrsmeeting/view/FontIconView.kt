package com.lrs.lrsmeeting.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

/**
 * @description 作用:
 * @date: 2020/4/7
 * @author: 卢融霜
 */
class FontIconView : androidx.appcompat.widget.AppCompatTextView {

    constructor (context: Context) : super(context) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        //设置字体图标
        val font = Typeface.createFromAsset(context.assets, "iconfont/iconfont.ttf")
        this.typeface = font
    }
}