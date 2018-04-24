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
        setContentView(R.layout.activity_main);

        tv_1 = (TextView) findViewById(R.id.tv_1);
        btn_1 = (Button) findViewById(R.id.btn_1);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv_1.setOnClickListener(this);
        btn_1.setOnClickListener(this);
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
        EventBus.getDefault().postSticky(new ChangeSecondActivityTextEvent("页面接收到eventBus消息"));
        Intent intent = new Intent(mContext, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeTextUI(String str) {
        tv_1.setText(str);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_1) {
            mPresenter.openPage();
        } else if (v.getId() == R.id.tv_1) {
            mPresenter.changeText();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangeMainActivityTextEvent event) {
        tv_1.setText(event.text);
        AndroidUtil.showOneToast(mContext, event.text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
