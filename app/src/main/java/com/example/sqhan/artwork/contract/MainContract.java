package com.example.sqhan.artwork.contract;

import com.example.sqhan.artwork.base.BasePresenter;
import com.example.sqhan.artwork.base.BaseView;

/**
 * Created by sqhan on 2018/4/23.
 */

public interface MainContract {
    interface Presenter extends BasePresenter {
        void changeText();//点击文字

        void openPage();//打开新页面
    }

    interface View extends BaseView<Presenter> {

        void changeTextUI(String str);

        void openPageUI();

    }

}
