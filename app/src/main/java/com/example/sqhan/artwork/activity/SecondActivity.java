package com.example.sqhan.artwork.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.model.events.ChangeMainActivityTextEvent;
import com.example.sqhan.artwork.model.events.ChangeSecondActivityTextEvent;
import com.example.sqhan.artwork.utils.AndroidUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by sqhan on 2018/4/23.
 */

public class SecondActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.second_activity_layout);
        tv_2 = (TextView) findViewById(R.id.tv_2);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv_2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_2) {
            EventBus.getDefault().post(new ChangeMainActivityTextEvent("发送事件成功"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent1(ChangeSecondActivityTextEvent event) {
        AndroidUtil.showOneToast(mContext, event.text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
