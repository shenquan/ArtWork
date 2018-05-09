package com.example.sqhan.artwork.di.module;

import com.example.sqhan.artwork.di.modle.Apple;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sqhan on 2018/5/9
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */
@Module
public class AppleModule {

    //此处需要返回Fruit构造函数中的参数对象
    @Provides
    Apple provideApple() {
        return new Apple();
    }

}
