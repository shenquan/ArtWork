package com.example.sqhan.artwork.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Modified by sqhan on 2018/5/11
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class PermissionConfig {

    private static PermissionConfig instance;
    private PermissionConfigInterface config;

    public interface PermissionConfigInterface {
        void showPermissionDialog(String message, Activity activity, DialogInterface.OnClickListener cancleListener, DialogInterface.OnClickListener settingListener);
    }

    public static PermissionConfig instance() {
        if (instance == null) {
            instance = new PermissionConfig();
        }
        return instance;
    }

    public void config(PermissionConfigInterface config) {
        this.config = config;
    }

    public PermissionConfigInterface getConfig() {
        return config;
    }

    void showPermissionDialog(String message, Activity activity, DialogInterface.OnClickListener cancleListener, DialogInterface.OnClickListener settingListener) {
        if (config == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity).setMessage(message)
                    .setPositiveButton("设置", settingListener)
                    .setNegativeButton("取消", cancleListener);
            builder.create().show();
        } else {
            config.showPermissionDialog(message, activity, cancleListener, settingListener);
        }
    }

}
