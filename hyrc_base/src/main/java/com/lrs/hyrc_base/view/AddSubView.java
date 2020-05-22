package com.lrs.hyrc_base.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lrs.hyrc_base.R;

import io.reactivex.annotations.Nullable;

/**
 * Created by Administrator on 2019/3/27 0027.
 * 自定义增减按钮 通过接口回调
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private FontIconView iv_sub;
    private FontIconView iv_add;
    private TextView tv_value;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 5;
    private Context mContext;

    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.add_sub_view, this);
        iv_sub = findViewById(R.id.iv_sub);
        iv_add = findViewById(R.id.iv_add);
        tv_value = findViewById(R.id.tv_value);
        this.mContext = context;
        int value = getValue();
        setValue(value);
        /**
         * 设置点击事件
         * */
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }


    public int getValue() {
        String valueStr = tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)) {
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value + "");
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.iv_sub) {
            subNumber();
        } else if (id == R.id.iv_add) {
            addNumber();
        }
        
    }

    private void addNumber() {
        if(value< maxValue){
            value ++;
        }
        setValue(value);
        if(onNumberChangerListener != null){
            onNumberChangerListener.onNumberChange(value);
        }
    }

    private void subNumber() {
        if (value > minValue) {
            value--;
        }
        setValue(value);
        if(onNumberChangerListener != null){
            onNumberChangerListener.onNumberChange(value);
        }
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 当数量发生变化的时候进行回调
     * */
    public interface  OnNumberChangerListener{
        /**
         * 当数据发生变化的时候回调
         * */
        void onNumberChange(int value);
    }

    private OnNumberChangerListener onNumberChangerListener;

    public void setOnNumberChangerListener(OnNumberChangerListener onNumberChangerListener) {
        this.onNumberChangerListener = onNumberChangerListener;
    }
}
