package com.example.sqhan.artwork.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kotlinmodule.TestActivity;
import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.contract.MainContract;
import com.example.sqhan.artwork.model.events.ChangeMainActivityTextEvent;
import com.example.sqhan.artwork.model.events.ChangeSecondActivityTextEvent;
import com.example.sqhan.artwork.presenter.MainPresenter;
import com.example.sqhan.artwork.utils.AndroidUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sqhan on 2018/5/1
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class MainActivity extends BaseActivity implements MainContract.IView {
    MainContract.IPresenter mPresenter;

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
    @BindView(R.id.main_open_a_page)
    Button mainOpenAPage;
    @BindView(R.id.image)
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        new MainPresenter(this);

//        mainOpenAPage.setText(null);//不会crash，因为源码里面判空了，若为null，则改为""。
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


    @Override
    public void setPresenter(MainContract.IPresenter presenter) {
        mPresenter = presenter;
    }

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
        if (mPresenter != null) {
            mPresenter.end();
            mPresenter = null;
        }
    }

    @OnClick({R.id.tv_1, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.open_leak_canary_activity, R.id.butter_knife_btn, R.id.main_open_a_page,
            R.id.openKotlinPage
    })
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
            case R.id.open_leak_canary_activity: {
                Intent intent = new Intent(mContext, LeakCanaryTestActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.butter_knife_btn:
                AndroidUtil.showOneToast(mContext, "我被点击了");
                ((TextView) view).setText("我改变了");
                break;
            case R.id.main_open_a_page: {
                Intent intent = new Intent(mContext, APageActivity.class);
                startActivity(intent);
            }
            case R.id.openKotlinPage: {
                Intent intent = new Intent(mContext, TestActivity.class);
                startActivity(intent);
            }

        }
    }

    @OnClick(R.id.image)
    public void onViewClicked() {
        AndroidUtil.showOneToast(mContext, "我是图片，我被点击了");
    }

}
