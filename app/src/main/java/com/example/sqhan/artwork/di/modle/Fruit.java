package com.example.sqhan.artwork.di.modle;

import javax.inject.Inject;

/**
 * Created by sqhan on 2018/5/9
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class Fruit {
    public Apple apple;//模拟apple是类似于OkHttpClient不可变的类，需要用module来进行依赖注入

    @Inject
    public Fruit(Apple apple) {
        this.apple = apple;
        apple.name = "我是苹果";
    }
}
