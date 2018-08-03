package com.example.sqhan.artwork.base;

import android.content.Context;

/**
 * Created by sqhan on 2018/4/23.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);

    Context getContext();
}
