package com.example.sqhan.artwork.di.component;

import com.example.sqhan.artwork.activity.SecondActivity;
import com.example.sqhan.artwork.di.module.AppleModule;

import dagger.Component;

/**
 * Created by sqhan on 2018/5/9
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */
//@Component(modules = {AppleModule.class, MainModule.class})//貌似这样可以同时注册两个。但最好不要这样，分别注册吧~
@Component(modules = {AppleModule.class})
public interface FruitComponent {
    void inject(SecondActivity secondActivity);
}
