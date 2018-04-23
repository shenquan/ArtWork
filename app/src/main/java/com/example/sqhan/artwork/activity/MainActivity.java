package com.example.sqhan.artwork.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.contract.MainContract;
import com.example.sqhan.artwork.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements MainContract.View, View.OnClickListener {

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainPresenter(this);

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

    }

    @Override
    public void changeTextUI(String str) {
        TextView tv = (TextView) findViewById(R.id.tv_1);
        tv.setText(str);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.tv_1).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_1) {
            mPresenter.openPage();
        } else if (v.getId() == R.id.tv_1) {
            mPresenter.changeText();
        }
    }
}
