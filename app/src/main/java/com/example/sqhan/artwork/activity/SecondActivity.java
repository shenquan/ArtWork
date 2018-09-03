package com.example.sqhan.artwork.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.di.component.DaggerFruitComponent;
import com.example.sqhan.artwork.di.component.DaggerSecondActivityComponent;
import com.example.sqhan.artwork.di.modle.Apple;
import com.example.sqhan.artwork.di.modle.Factory;
import com.example.sqhan.artwork.di.modle.Fruit;
import com.example.sqhan.artwork.di.modle.Product;
import com.example.sqhan.artwork.di.module.AppleModule;
import com.example.sqhan.artwork.model.events.ChangeMainActivityTextEvent;
import com.example.sqhan.artwork.model.events.ChangeSecondActivityTextEvent;
import com.example.sqhan.artwork.utils.AndroidUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sqhan on 2018/4/23
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class SecondActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "hsq";
    @Inject
    Product product;
    @Inject
    Factory factory;
    @Inject
    Apple apple;
    @Inject
    Fruit fruit;
    private TextView tv_2;

    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //方式一:用create的方式，其返回的是new Builder().build(); 我故意命名为injectXXX，其实一般用inject即可。
//        DaggerSecondActivityComponent.create().injectXXX(this);
        //方式二:用builder的方式，我推荐用这个，因为带module的就是用的这个
        DaggerSecondActivityComponent.builder().build().injectXXX(this);
        DaggerFruitComponent.builder().appleModule(new AppleModule()).build().inject(this);

//        Log.e(TAG, "Product的属性i=" + product.i);
//        Log.e(TAG, "Factory的属性j=" + factory.j);
//        Log.e(TAG, "Apple的属性name=" + apple.name);
//        Log.e(TAG, "Fruit中Apple的属性name=" + fruit.apple.name);
        // 测试onSubscribe执行再什么线程
//        new Thread(this::rxjavaTest1).start();
        rxjavaTest1();
        rxjavaText2();

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
        /**
         * 背景：在发送网络请求时 退出当前Activity
         冲突：此时如果回到主线程更新 UI，App会崩溃
         解决方案：当 Activity退出时，调用 Disposable.dispose()切断观察者和被观察者的连接，使得观察者无法收到事件 & 响应事件
         当出现多个Disposable时，可采用RxJava内置容器CompositeDisposable进行统一管理
         */
        //            CompositeDisposable compositeDisposable=new CompositeDisposable();
        // 解除订阅，防止崩溃
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * Rxjava 2.0测试
     * https://www.jianshu.com/p/a406b94f3188
     * https://www.jianshu.com/p/5225b2baaecd
     */
    private void rxjavaTest1() {
        // RxJava的流式操作
        // 1. 创建被观察者 & 生产事件
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            Log.e(TAG, " 被观察者 Observable的工作线程是: " + Thread.currentThread().getName());
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                    // 3. 创建观察者 & 定义响应事件的行为
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        // 如果没有主动切换线程，则其实在rxjavaTest1()函数所在的线程中执行
                        Log.e(TAG, " 观察者 Observer的工作线程是: " + Thread.currentThread().getName());
                        Log.e(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.e(TAG, "对Next事件" + value + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "对Complete事件作出响应");
                    }

                });
    }

    /**
     * 自己写的rxjava测试例子
     */
    private void rxjavaText2() {
//        Observable.

    }


}
