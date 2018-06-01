package com.example.sqhan.artwork.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created by sqhan on 2018/4/23.
 */

public class BaseApplication extends Application {
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication baseApplication = (BaseApplication) context.getApplicationContext();
        return baseApplication.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);//调试H5
        Bugly.init(this, "888efb4951", false);
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }*/
        refWatcher = LeakCanary.install(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 安装tinker
        Beta.installTinker();
    }
}
