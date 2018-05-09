package com.example.sqhan.artwork.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.service.ForegroundService;

/**
 * Created by sqhan on 2018/5/3
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class ForegroundServiceActivity extends BaseActivity implements View.OnClickListener {
    private Button startForeground;
    private Button stopForeground;
    private Button notify_btn;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(mContext, ForegroundService.class);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.foreground_activity_layout);
        startForeground = (Button) findViewById(R.id.startForeground);
        stopForeground = (Button) findViewById(R.id.stopForeground);
        notify_btn = (Button) findViewById(R.id.notify_btn);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        startForeground.setOnClickListener(this);
        stopForeground.setOnClickListener(this);
        notify_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.startForeground) {
            intent.putExtra("cmd", 0);
            startService(intent);
        } else if (id == R.id.stopForeground) {
            intent.putExtra("cmd", 1);
            stopService(intent);
        } else if (id == R.id.notify_btn) {
            openNotification();
        }
    }

    /**
     * Notification
     */
    public void openNotification() {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //使用兼容版本
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置状态栏的通知图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知栏横条的图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.common_bug_img));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //右上角的时间显示
        builder.setShowWhen(true);
        //设置通知栏的标题内容
        builder.setContentTitle("我是普通通知，点我之后我会消失");
        //通知栏打开新的Activity
        Intent intent = new Intent(mContext, NotificationOpenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//关键的一步，设置启动模式。我测试，不加这句也可以，此只是让其显示唯一。
        PendingIntent pi = PendingIntent.getActivities(mContext, 0, new Intent[]{intent}, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pi);
        //创建通知
        Notification notification = builder.build();
        //设置点击通知的反应.因为此为服务，服务没有关闭，所以即使设置了下面flags点击也不会消失，只有停止服务才可以
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // 点击清除按钮或点击通知后会自动消失
        if (notifyManager != null) {
            notifyManager.notify(11, notification);
        }

    }
}
