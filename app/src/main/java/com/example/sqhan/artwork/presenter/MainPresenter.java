package com.example.sqhan.artwork.presenter;

import android.content.Context;

import com.example.sqhan.artwork.contract.MainContract;

/**
 * Created by sqhan on 2018/4/23.
 */

public class MainPresenter implements MainContract.IPresenter {
    private Context mContext;
    private MainContract.IView mView;

    public MainPresenter(MainContract.IView view) {
        mContext = view.getContext();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void changeText() {
        if (mView == null) {
            return;
        }
        mView.changeTextUI("改变之后最终的文本");
    }

    @Override
    public void openPage() {
        if (mView == null) {
            return;
        }
        mView.openPageUI();
    }

    @Override
    public void openSimpleServicePage() {
        if (mView == null) {
            return;
        }
        mView.openSimpleServicePageUI();
    }

    @Override
    public void openBindServicePage() {
        if (mView == null) {
            return;
        }
        mView.openBindServicePageUI();
    }

    @Override
    public void openForegroundServicePage() {
        if (mView == null) {
            return;
        }
        mView.openForegroundServicePageUI();
    }


    @Override
    public void start() {

    }

    @Override
    public void end() {
        mView = null;
    }
}
