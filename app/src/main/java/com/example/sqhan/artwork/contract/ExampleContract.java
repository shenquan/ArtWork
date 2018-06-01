package com.example.sqhan.artwork.contract;

import com.example.sqhan.artwork.base.BasePresenter;
import com.example.sqhan.artwork.base.BaseView;

/**
 * Created by sqhan on 2018/4/23.
 */

public interface ExampleContract {
    interface IPresenter extends BasePresenter {

    }

    interface IView extends BaseView<IPresenter> {
        void showToast();
    }

}
