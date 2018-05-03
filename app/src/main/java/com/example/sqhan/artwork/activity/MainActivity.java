package com.example.sqhan.artwork.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.contract.MainContract;
import com.example.sqhan.artwork.model.events.ChangeMainActivityTextEvent;
import com.example.sqhan.artwork.model.events.ChangeSecondActivityTextEvent;
import com.example.sqhan.artwork.presenter.MainPresenter;
import com.example.sqhan.artwork.utils.AndroidUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements MainContract.View, View.OnClickListener {
    private TextView tv_1;
    private Button btn_1;
    private Button btn_2;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainPresenter(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void initView() {
        super.initView();
        //在这里setContentView
        setContentView(R.layout.activity_main);

        tv_1 = (TextView) findViewById(R.id.tv_1);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv_1.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openPageUI() {
        Intent intent = new Intent(mContext, SecondActivity.class);
        startActivity(intent);
        EventBus.getDefault().postSticky(new ChangeSecondActivityTextEvent("页面接收到eventBus消息"));
    }

    @Override
    public void changeTextUI(String str) {
        tv_1.setText(str);
    }

    @Override
    public void openSimpleServicePageUI() {
        Intent intent = new Intent(mContext,SimpleServiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_1) {
            mPresenter.openPage();
        } else if (v.getId() == R.id.tv_1) {
            mPresenter.changeText();
        } else if (v.getId() == R.id.btn_2) {
            mPresenter.openSimpleServicePage();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangeMainActivityTextEvent event) {
        tv_1.setText(event.text);
        AndroidUtil.showOneToast(mContext, event.text);
    }

    /**
     * 即使是两个相同的事件，也都会执行
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent11(ChangeMainActivityTextEvent event) {
        tv_1.setText(event.text);
        AndroidUtil.showOneToast(mContext, "111");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
