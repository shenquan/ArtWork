package com.example.sqhan.artwork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sqhan.artwork.base.BaseFragment;
import com.example.sqhan.artwork.contract.ExampleContract;
import com.example.sqhan.artwork.utils.AndroidUtil;

/**
 * Created by sqhan on 2018/4/23.
 */

public class ExampleFragment extends BaseFragment implements ExampleContract.View {
    private ExampleContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //在对应的presenter中执行该方法：mView.setPresenter(this);
    //改为使用dagger2注入
    /*@Override
    public void setPresenter(ExampleContract.Presenter presenter) {
        mPresenter = presenter;
    }*/

    @Override
    public void showToast() {
        //getContext是Fragment获取所在Activity的方法
        AndroidUtil.showOneToast(getContext(), "fragment显示toast");
    }


}
