package com.example.sqhan.artwork.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.contract.MainContract;
import com.example.sqhan.artwork.di.component.DaggerMainActivityComponent;
import com.example.sqhan.artwork.di.module.MainModule;
import com.example.sqhan.artwork.model.events.ChangeMainActivityTextEvent;
import com.example.sqhan.artwork.model.events.ChangeSecondActivityTextEvent;
import com.example.sqhan.artwork.presenter.MainPresenter;
import com.example.sqhan.artwork.utils.AndroidUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sqhan on 2018/5/1
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class MainActivity extends BaseActivity implements MainContract.View {
    @Inject
    MainPresenter mPresenter;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_4)
    Button btn4;
    @BindView(R.id.open_leak_canary_activity)
    Button openLeakCanaryActivity;
    @BindView(R.id.butter_knife_btn)
    Button butterKnifeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        new MainPresenter(this);
        getPrim();

        DaggerMainActivityComponent.builder().mainModule(new MainModule(this)).build().inject(this);


    }
    //是否选择使用，选择性的重写这两个方式
    /*@Override
    protected void initView() {
        super.initView();
        //在这里setContentView
        setContentView(R.layout.activity_main);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
    }*/


    //改为使用dagger2注入
    /*@Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }*/

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void openPageUI() {
        Intent intent = new Intent(mContext, SecondActivity.class);
        startActivity(intent);
        EventBus.getDefault().postSticky(new ChangeSecondActivityTextEvent("页面接收到eventBus消息"));
    }

    @Override
    public void changeTextUI(String str) {
        tv1.setText(str);
    }

    @Override
    public void openSimpleServicePageUI() {
        Intent intent = new Intent(mContext, SimpleServiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void openBindServicePageUI() {
        Intent intent = new Intent(mContext, BindServiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void openForegroundServicePageUI() {
        Intent intent = new Intent(mContext, ForegroundServiceActivity.class);
        startActivity(intent);
    }

    /*@Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_1) {
            mPresenter.openPage();
        } else if (id == R.id.tv_1) {
            mPresenter.changeText();
        } else if (id == R.id.btn_2) {//打开简单启动服务页面
            mPresenter.openSimpleServicePage();
        } else if (id == R.id.btn_3) {//打开绑定服务页面
            mPresenter.openBindServicePage();
        } else if (id == R.id.btn_4) {
            mPresenter.openForegroundServicePage();//打开前台服务页面
        } else if (id == R.id.openLeakCanaryActivity) {
            Intent intent = new Intent(mContext, LeakCanaryTestActivity.class);
            startActivity(intent);
        }
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangeMainActivityTextEvent event) {
        tv1.setText(event.text);
        AndroidUtil.showOneToast(mContext, event.text);
    }

    /**
     * 即使是两个相同的事件，也都会执行
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent11(ChangeMainActivityTextEvent event) {
        tv1.setText(event.text);
        AndroidUtil.showOneToast(mContext, "111");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //相关权限申请
    private void getPrim() {
        if (Build.VERSION.SDK_INT >= 23) {
            final List<String> permissionsList = new ArrayList<String>();
            addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE);
            addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            addPermission(permissionsList, Manifest.permission.CAMERA);
//            addPermission(permissionsList, Manifest.permission.CALL_PHONE);
            addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION);
            addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION);
            //   addPermission(permissionsList,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
            if (permissionsList.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0x111);
            } else {
//                gotoMainActivity();
            }
        } else {
//            gotoMainActivity();
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            return false;
        }
        return true;
    }

    @OnClick({R.id.tv_1, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.open_leak_canary_activity, R.id.butter_knife_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                mPresenter.changeText();
                break;
            case R.id.btn_1:
                mPresenter.openPage();
                break;
            case R.id.btn_2:
                //打开简单启动服务页面
                mPresenter.openSimpleServicePage();
                break;
            case R.id.btn_3:
                //打开绑定服务页面
                mPresenter.openBindServicePage();
                break;
            case R.id.btn_4:
                //打开前台服务页面
                mPresenter.openForegroundServicePage();
                break;
            case R.id.open_leak_canary_activity:
                Intent intent = new Intent(mContext, LeakCanaryTestActivity.class);
                startActivity(intent);
                break;
            case R.id.butter_knife_btn:
                AndroidUtil.showOneToast(mContext, "我被点击了");
                ((TextView) view).setText("我改变了");
                break;
        }
    }
}
