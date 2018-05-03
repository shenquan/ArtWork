package com.example.sqhan.artwork.presenter;

import android.content.Context;

import com.example.sqhan.artwork.contract.MainContract;

/**
 * Created by sqhan on 2018/4/23.
 */

public class MainPresenter implements MainContract.Presenter {
    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mContext = view.getContext();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void changeText() {
        mView.changeTextUI("改变之后的文本");
    }

    @Override
    public void openPage() {
        mView.openPageUI();
    }

    @Override
    public void openSimpleServicePage() {
        mView.openSimpleServicePageUI();
    }
}
