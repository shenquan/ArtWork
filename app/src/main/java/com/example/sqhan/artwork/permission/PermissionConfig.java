package com.example.sqhan.artwork.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by dev on 2017/5/25.
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
//            AlertDialog.Builder builder = new AlertDialog.Builder(activity).setMessage(message)
//                    .setPositiveButton("设置", settingListener)
//                    .setNegativeButton("取消", cancleListener);
            //把取消按钮去掉
            AlertDialog.Builder builder = new AlertDialog.Builder(activity).setMessage(message)
                    .setPositiveButton("设置", settingListener);
            builder.create().show();
        } else {
            config.showPermissionDialog(message, activity, cancleListener, settingListener);
        }
    }

}
