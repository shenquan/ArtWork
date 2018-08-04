package com.example.sqhan.artwork.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sqhan.artwork.activity.BindServiceActivity;

/**
 * 直接启动service
 */
public class SimpleService extends Service {
    public static final String TAG = "hsq";

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        System.out.println("onCreate invoke");
        Log.e(TAG, "onCreate invoke");
        super.onCreate();
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand invoke");
        Log.e(TAG, "onStartCommand invoke");
        Intent myIntent = new Intent(this, BindServiceActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        Context context1 = getBaseContext();
//        Context context2 = SimpleService.this;
        PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext(), 0, new Intent[]{myIntent}, PendingIntent.FLAG_CANCEL_CURRENT);

        new Handler().postDelayed(() -> {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
//                startActivity(myIntent);
        }, 2000);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        System.out.println("onDestroy invoke");
        Log.e(TAG, "onDestroy invoke");
        super.onDestroy();
    }

    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
