package com.example.sqhan.artwork.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.di.component.DaggerSecondActivityComponent;
import com.example.sqhan.artwork.di.modle.Factory;
import com.example.sqhan.artwork.di.modle.Product;
import com.example.sqhan.artwork.model.events.ChangeMainActivityTextEvent;
import com.example.sqhan.artwork.model.events.ChangeSecondActivityTextEvent;
import com.example.sqhan.artwork.utils.AndroidUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by sqhan on 2018/4/23.
 */

public class SecondActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "HSQ";
    private TextView tv_2;
    Product product;
    @Inject
    Factory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //方式一:用create的方式，其返回的是new Builder().build(); 我故意命名为injectXXX，其实一般用inject即可。
//        DaggerSecondActivityComponent.create().injectXXX(this);
        //方式二:用builder的方式，我推荐用这个，因为带module的就是用的这个
        DaggerSecondActivityComponent.builder().build().injectXXX(this);

//        Log.e(TAG, "Product的属性i=" + product.i);
        Log.e(TAG, "Factory的属性j=" + factory.product.i);

    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.second_activity_layout);
        tv_2 = (TextView) findViewById(R.id.tv_2);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        tv_2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_2) {
            EventBus.getDefault().post(new ChangeMainActivityTextEvent("发送事件成功"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent1(ChangeSecondActivityTextEvent event) {
        AndroidUtil.showOneToast(mContext, event.text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
