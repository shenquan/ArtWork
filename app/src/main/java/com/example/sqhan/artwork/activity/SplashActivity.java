package com.example.sqhan.artwork.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.permission.PermissionListener;
import com.example.sqhan.artwork.permission.PermissionUtils;
import com.example.sqhan.artwork.permission.PermissionsDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sqhan on 2018/5/11.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermissions();//请求权限
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.splash_layout);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private static final int PERMISSION_REQUEST_CODE = 11;
    private String[] requestPermissionsArr = new String[]{
//            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写权限一旦被赋予，读权限即被赋予
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.CALL_PHONE,

    };

    //可以检测一个权限或者多个权限。
    //适合点击相机检测是否有权限使用。不适合splash页面轮询。
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionsDispatcher.checkPermissions((Activity) mContext, PERMISSION_REQUEST_CODE, new PermissionListener() {
                @Override
                public void onPermissionsGranted(int requestCode, int[] grantResults, String... permissions) {

                }

                @Override
                public void onPermissionsDenied(int requestCode, int[] grantResults, String... permissions) {
                    if (permissions != null && permissions.length > 0) {
                    }
                }

                @Override
                public void onShowRequestPermissionRationale(int requestCode, boolean isShowRationale, String... permissions) {
                    if (permissions != null && permissions.length > 0) {

                    }
                }

                @Override
                public void onPermissionsError(int requestCode, int[] grantResults, String errorMsg, String... permissions) {

                }
            }, requestPermissionsArr);
        } else {
            goToMain();
        }
    }

    /**
     * 得到未被授权的权限
     */
    private List<String> getNotAuthorizationList() {
        final List<String> permissionsList = new ArrayList<>();
        for (String permission : requestPermissionsArr) {
            addPermission(permissionsList, permission);
        }
        return permissionsList;
    }

    /**
     * 获取相关权限申请
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            final List<String> permissionsList = getNotAuthorizationList();
            if (permissionsList.size() > 0) {
                PermissionsDispatcher.getNotAuthorizationPermissionsArray((Activity) mContext, requestPermissionsArr);
                if (PermissionUtils.getNeedRequestPermissions().size() > 0) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), PERMISSION_REQUEST_CODE);
                } else if (PermissionUtils.getUnshowedPermissions().size() > 0) {
                    PermissionsDispatcher.handleNotRemind((Activity) mContext);
                }
            } else {
                goToMain();
            }
        } else {
            goToMain();
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            return false;
        }
        return true;
    }

    private void goToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                //换种写法打开activity
                ComponentName componentName = new ComponentName(mContext, MainActivity.class);
                intent.setComponent(componentName);
                startActivity(intent);

                SplashActivity.this.finish();
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (getNotAuthorizationList().size() > 0) {
                getPermissions();
            } else {
                goToMain();
            }
        }
    }


}
