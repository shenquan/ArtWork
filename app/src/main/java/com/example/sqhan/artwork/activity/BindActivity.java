package com.example.sqhan.artwork.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.service.LocalService;

/**
 * Created by sqhan on 2018/5/3.
 */

public class BindActivity extends BaseActivity implements View.OnClickListener {
    private final static String TAG = "hsq";

    private Button btnBind;
    private Button btnUnBind;
    private Button btnGetDatas;

    /**
     * ServiceConnection代表与服务的连接，它只有两个方法，
     * onServiceConnected和onServiceDisconnected，
     * 前者是在操作者在连接一个服务成功时被调用，而后者是在服务崩溃或被杀死导致的连接中断时被调用
     */
    private ServiceConnection conn;
    private LocalService mService;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.bind_service_layout);

        btnBind = (Button) findViewById(R.id.BindService);
        btnUnBind = (Button) findViewById(R.id.unBindService);
        btnGetDatas = (Button) findViewById(R.id.getServiceDatas);
        //创建绑定对象
        intent = new Intent(this, LocalService.class);
        conn = new ServiceConnection() {
            /**
             * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
             * 通过这个IBinder对象，实现宿主和Service的交互。
             */
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(TAG, "绑定成功调用：onServiceConnected");
                // 获取Binder
                LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
                mService = binder.getService();
            }

            /**
             * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
             * 例如内存的资源不足时这个方法才被自动调用。
             */
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e(TAG, "绑定解除调用：onServiceDisconnected");
                mService = null;
            }
        };
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        btnBind.setOnClickListener(this);
        btnUnBind.setOnClickListener(this);
        btnGetDatas.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.BindService) {
            Log.e(TAG, "绑定调用：bindService");
            //调用绑定方法
            bindService(intent, conn, Service.BIND_AUTO_CREATE);
        } else if (v.getId() == R.id.unBindService) {
            Log.e(TAG, "解除绑定调用：unbindService");
            // 解除绑定
            if (mService != null) {
                mService = null;
                unbindService(conn);
            }
        } else if (v.getId() == R.id.getServiceDatas) {
            if (mService != null) {
                // 通过绑定服务传递的Binder对象，获取Service暴露出来的数据

                Log.e(TAG, "从服务端获取数据：" + mService.getCount());
            } else {

                Log.e(TAG, "还没绑定呢，先绑定,无法从服务端获取数据");
            }
        }

    }
}
