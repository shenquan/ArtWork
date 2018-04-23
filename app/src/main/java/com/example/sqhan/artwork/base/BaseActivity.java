package com.example.sqhan.artwork.base;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.sqhan.artwork.utils.AndroidUtil;

/**
 * Created by sqhan on 2018/4/23.
 */

public class BaseActivity extends FragmentActivity {
    protected Context mContext;
    private Typeface mTypeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/my_iconfont.ttf");
        initView();

        //调试环境显示调试按钮
        if (AndroidUtil.isDebugEnv(mContext)) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    //// TODO: 2018/4/23

                }
            });
        }

    }

    protected void initView() {

    }

    //设置字体库的字体
    protected void setIconfont(TextView textView) {
        textView.setTypeface(mTypeface);
    }

}
