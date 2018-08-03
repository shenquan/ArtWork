package com.example.sqhan.artwork.presenter;

import android.content.Context;

import com.example.sqhan.artwork.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by sqhan on 2018/4/23.
 */

public class MainPresenter implements MainContract.IPresenter {
    private Context mContext;
    private MainContract.IView mView;

    @Inject
    public MainPresenter(MainContract.IView view) {
        mContext = view.getContext();
        mView = view;
//        mView.setPresenter(this);//改为使用dagger2注入
    }

    @Override
    public void changeText() {
//        mView.changeTextUI("改变之后的文本");
        mView.changeTextUI("改变之后的最终文本");
    }

    @Override
    public void openPage() {
        mView.openPageUI();
    }

    @Override
    public void openSimpleServicePage() {
        mView.openSimpleServicePageUI();
    }

    @Override
    public void openBindServicePage() {
        mView.openBindServicePageUI();
    }

    @Override
    public void openForegroundServicePage() {
        mView.openForegroundServicePageUI();
    }
}
