package com.example.sqhan.artwork.lottie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import rx.Observable;
import rx.functions.Action1;

/**
 * 默认语音面板小机器人动画管理类
 */
public class DefaultRobotAnimManager {

    private static final String ANIM_FILE_NAME = "white_box.json";

    /**
     * 初始化要播放动画的 view
     *
     * @param view 目标 view
     */
    public void initView(LottieAnimationView view) {
        view.setAnimation(ANIM_FILE_NAME);
    }

    /**
     * 上升动画
     *
     * @param animationView  目标 view
     * @param callbackAimEnd 结束回调
     */
    public void playRise(LottieAnimationView animationView, Action1<Boolean> callbackAimEnd) {
        animationView.setSpeed(-1);
        animationView.setRepeatCount(0);
        animationView.setMinAndMaxProgress(0.8358f, 1f);
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.removeAllAnimatorListeners();
                if (null != callbackAimEnd) {
                    Observable.just(true).subscribe(callbackAimEnd);
                }
            }
        });
        animationView.playAnimation();
    }

    /**
     * 下沉动画
     *
     * @param animationView  目标 view
     * @param callbackAimEnd 结束回调
     */
    public void playDown(LottieAnimationView animationView, Action1<Boolean> callbackAimEnd) {
        animationView.setSpeed(1);
        animationView.setRepeatCount(0);
        animationView.setMinAndMaxProgress(0.876f, 1f);
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.removeAllAnimatorListeners();
                if (callbackAimEnd != null) {
                    Observable.just(true).subscribe(callbackAimEnd);
                }
            }
        });
        animationView.playAnimation();
    }

    /**
     * 通知动画
     *
     * @param animationView  目标 view
     * @param callbackAimEnd 结束回调
     */
    public void playNotify(LottieAnimationView animationView, Action1<Boolean> callbackAimEnd) {
        animationView.setSpeed(1);
        animationView.setRepeatCount(0);
        animationView.setMinAndMaxProgress(0.2836f, 0.8358f);
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.removeAllAnimatorListeners();
                if (callbackAimEnd != null) {
                    Observable.just(true).subscribe(callbackAimEnd);
                }
            }
        });
        animationView.playAnimation();

    }

    /**
     * 闲置状态动画
     *
     * @param animationView 目标 view
     */
    public void playIdle(LottieAnimationView animationView) {
        animationView.setSpeed(1);
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        animationView.setMinAndMaxProgress(0, 0.2836f);
        animationView.playAnimation();
    }

}
