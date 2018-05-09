package com.example.sqhan.artwork.di.component;

import com.example.sqhan.artwork.activity.MainActivity;
import com.example.sqhan.artwork.di.module.MainModule;

import dagger.Component;

/**
 * Created by sqhan on 2018/5/9
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */
@Component(modules = MainModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
