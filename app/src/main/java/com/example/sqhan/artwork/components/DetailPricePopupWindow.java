package com.example.sqhan.artwork.components;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.utils.AndroidUtil;


/**
 * Created by sqhan on 2017/5/24.
 */

public class DetailPricePopupWindow extends PopupWindow {
    private Context mContext;
    public View mView;

    public DetailPricePopupWindow(Context context) {
        this.mContext = context;
        this.mView = LayoutInflater.from(mContext).inflate(R.layout.detail_popup_window, null);

        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.mView);
//        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        int screenHeight = AndroidUtil.getScreenHeight(mContext);
        int height = (int) (screenHeight * 0.7);
        this.setHeight(height);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体未覆盖手机屏幕的部分是否可以点击，
        // 若设置为true，则点击该控件外部的部分即点击地图不会将点击事件传递进去导致不会清除变大的游客集合
        this.setFocusable(true);
        // 设置弹出窗体的背景，指的是弹出的价格套系的背景
        ColorDrawable colorDrawable = new ColorDrawable(0xFFFFFFFF);
        this.setBackgroundDrawable(colorDrawable);
        //动画效果
        this.setAnimationStyle(R.style.DetailPopupWindow);


    }


}
