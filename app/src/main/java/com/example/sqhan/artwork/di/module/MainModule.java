package com.example.sqhan.artwork.di.module;

import com.example.sqhan.artwork.contract.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sqhan on 2018/5/9
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */
@Module
public class MainModule {
    private final MainContract.IView view;

    public MainModule(MainContract.IView view) {
        this.view = view;
    }

    @Provides
    MainContract.IView provideMainContractView() {
        return view;
    }

}
