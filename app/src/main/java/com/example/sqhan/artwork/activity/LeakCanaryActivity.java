package com.example.sqhan.artwork.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.base.BaseApplication;
import com.example.sqhan.artwork.other.TestManager;
import com.squareup.leakcanary.RefWatcher;

public class LeakCanaryActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestManager.getInstance(mContext);

        LeakThread leakThread = new LeakThread();
        leakThread.start();
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_leakcanary);


    }

    @Override
    protected void initEvents() {
        super.initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("hsq", "LeakCanaryActivity执行了onDestroy");
        RefWatcher refWatcher = BaseApplication.getRefWatcher(mContext);
        refWatcher.watch(this);
    }

    class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(6 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
