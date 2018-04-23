package com.example.sqhan.artwork.contract;

import com.example.sqhan.artwork.base.BasePresenter;
import com.example.sqhan.artwork.base.BaseView;

/**
 * Created by sqhan on 2018/4/23.
 */

public interface ExampleContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void showToast();
    }

}
