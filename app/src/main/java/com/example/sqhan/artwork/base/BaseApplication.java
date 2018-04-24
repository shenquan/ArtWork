package com.example.sqhan.artwork.base;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.tencent.bugly.Bugly;

/**
 * Created by sqhan on 2018/4/23.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);//调试H5
        Bugly.init(this, "888efb4951", false);
    }
}
