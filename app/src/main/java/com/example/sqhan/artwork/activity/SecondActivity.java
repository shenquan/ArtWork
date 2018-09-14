package com.example.sqhan.artwork.activity;

import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.PaintCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
    // 不用上面的，直接用这个即可
    // 用于页面销毁时是确保取消订阅
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private TextView tv_1;
    private TextView tv_2;
    private Disposable disposable;
    private LinearLayout ll;

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

//        rxjavaTest1();
//        rxjavaText2();
//        rxjavaText3();

//        int unicode1 = 0x1F1F8;
        int unicode1 = 0x1F601;
        //下面这两个组合在一起
//        int unicode2 = 0x1F1E8;
//        int unicode3 = 0x1F1F3;
        int unicode2 = 0x0034;
        int unicode3 = 0x20E3;
        String str1 = getEmojiByUnicode(unicode1);
        String str2 = getEmojiByUnicode(unicode2);
        String str3 = getEmojiByUnicode(unicode3);
        tv_1.setText(str1);
//        tv_2.setText(str2+str3);
        String str4 = getEmojiByUnicode(0x260e);
        tv_2.setText(str4);
//        tv_2.setText("😀\uD83D\uDE00");

        //此处的hasGlyph()还是不能判断比如0x360e，显示成头像，
        if (PaintCompat.hasGlyph(new Paint(), str4)) {
            Log.e(TAG, "true");
        } else {
            Log.e(TAG, "false");
        }

        View inflateView = LayoutInflater.from(this).inflate(R.layout.inflate_layout, null);
        ll.addView(inflateView);

        tv_2.setOnClickListener(v -> {
            //值是像素
            ObjectAnimator animator = ObjectAnimator.ofFloat(inflateView, "translationY", -270f, 0);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(650);
            animator.start();
        });


    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.second_activity_layout);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        ll = findViewById(R.id.ll);

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

        //不用上面的，直接用这个解除订阅即可
        mCompositeDisposable.clear();
    }

    /**
     * Rxjava 2.0测试
     * https://www.jianshu.com/p/a406b94f3188
     * https://www.jianshu.com/p/5225b2baaecd
     */
    private void rxjavaTest1() {
        // RxJava的流式操作
        // 1. 创建被观察者 & 生产事件
        // todo 直接Observable.create出现黄色警告，原因是若subscribe中的参数为consumer，则返回的是Disposable，需要赋值
        // 若是new Observer，则不用重新赋值，不会报黄色警告
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            Log.e(TAG, " 被观察者 Observable的工作线程是: " + Thread.currentThread().getName());
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable -> Log.e(TAG, " doOnSubscribe()的工作线程是: " + Thread.currentThread().getName()))
//                .subscribeOn(Schedulers.io()) //doOnSubscribe()在其后的subscribeOn()指定的线程执行
                .subscribe(new Observer<Integer>() {
                    // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                    // 3. 创建观察者 & 定义响应事件的行为
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        // 默认最先调用复写的 onSubscribe（），若则其实在rxjavaTest1()函数在UI线程，则在这里可以提前更新UI
                        // 测试结论：如果没有主动切换线程，则其实在rxjavaTest1()函数所在的线程中执行，
                        // 没必要用doOnSubscribe()函数
                        Log.e(TAG, " 观察者 Observer的工作线程是: " + Thread.currentThread().getName());
                        Log.e(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.e(TAG, " onNext的工作线程是: " + Thread.currentThread().getName());
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
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");
                emitter.onComplete();
            }
        });

//        observable = Observable.just("A", "B", "C");
        String[] words = {"A", "B", "C"};
//        observable = Observable.fromArray(words);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete()");
            }
        };
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    /**
     * 自己测试代码
     */
    private void rxjavaText3() {
//        Disposable disposable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("1");
//                emitter.onNext("2");
//                emitter.onNext("3");
////                emitter.onComplete();
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.e(TAG, s);
//                    }
//                });

        // 或者
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
//                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 云霄的代码参考
     *
     */
    //Disposable disposable = Observable.just(resultBitmap)
    //                .map(bitmap -> {
    //                    if (Macro.IS_DEBUG) {
    //                        OcrHelper.saveTempImageData(OcrMaskActivity.this, OcrHelper.bitmap2Bytes(bitmap));
    //                    }
    //                    Bitmap fixBitmap = OcrHelper.constraintLength(bitmap);
    //                    return OcrHelper.encodeImage(OcrMaskActivity.this, fixBitmap);
    //                })
    //                .doOnNext(encodeImageData -> {
    //                    NetworkStateReceiver.requestNetworkStateDirectly();
    //                    if (!NetworkStateUtils.isNetworkConnected()) {
    //                        throw new OcrException(getString(R.string.ocr_no_internet_error));
    //                    }
    //                    if (TextUtils.isEmpty(encodeImageData)) {
    //                        throw new OcrException(getString(R.string.ocr_image_encode_error));
    //                    }
    //                })
    //                .flatMap(encodeImageData -> {
    //                    if (OcrHelper.sOcrType == OcrHelper.OCR_NORMAL_TYPE) {
    //                        return APIWrapper.ocr(encodeImageData);
    //                    } else {
    //                        return APIWrapper.ocrCertificate(encodeImageData, OcrHelper.sOcrType);
    //                    }
    //                })
    //                .map(response -> {
    //                    //文字扫描
    //                    if (OcrHelper.sOcrType == OcrHelper.OCR_NORMAL_TYPE && response instanceof OcrResultBean) {
    //                        OcrResultBean resultBean = (OcrResultBean) response;
    //                        StringBuilder sb = new StringBuilder();
    //                        List<OcrResultBean.WordsResultBean> wordsResult = resultBean.getWordsResult();
    //                        for (OcrResultBean.WordsResultBean bean : wordsResult) {
    //                            sb.append(bean.getWords()).append("\n");
    //                        }
    //                        if (TextUtils.isEmpty(sb)) {
    //                            throw new OcrException(getString(R.string.ocr_no_result_error));
    //                        }
    //                        return sb.toString();
    //                    }
    //                    //表格或者证件扫描
    //                    if (response instanceof BaseBean<?>) {
    //                        BaseBean<JsonObject> resultBean = (BaseBean<JsonObject>) response;
    //                        if (OcrHelper.sOcrType == OcrHelper.OCR_TYPE_CHART) {
    //                            //表格
    //                            if (!resultBean.data.get("form_content_status").getAsBoolean()) {
    //                                throw new OcrException(getString(R.string.ocr_error));
    //                            }
    //                            return OcrHelper.writeExcel(OcrMaskActivity.this,
    //                                    resultBean.data.get("form_content").toString());
    //                        } else {
    //                            //证件
    //                            if (resultBean.error != 0 || resultBean.data == null
    //                                    || resultBean.data.get("error_code") != null) {
    //                                throw new OcrException(getString(R.string.ocr_error));
    //                            }
    //                            return resultBean.data.toString();
    //                        }
    //                    }
    //                    return "";
    //                })
    //                .subscribeOn(Schedulers.io())
    //                .observeOn(AndroidSchedulers.mainThread())
    //                .subscribe(this::handleMaskSuccess, this::handleMaskFail);
    //        mCompositeDisposable.add(disposable);

}

//rxjava 1.x版本的
       /* Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.e(TAG, "completed");
            }
        };

        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
// 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
// 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
*/
/**
 * 而与 Subscriber.onStart() 相对应的，有一个方法 Observable.doOnSubscribe() 。
 * 它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程。
 * 默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；
 * 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
 */
//        Observable.create()
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        progressBar.setVisibility(View.VISIBLE); // 需要在主线程执行
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);


// rxjava 2版本之后已经没了Subscriber,也没有onStart了
//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };

