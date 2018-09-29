//package com.example.sqhan.artwork.lottie;
//
//import android.content.Context;
//import android.view.View;
//
//import com.hwangjr.rxbus.RxBus;
//import com.hwangjr.rxbus.annotation.Subscribe;
//import com.hwangjr.rxbus.annotation.Tag;
//import com.hwangjr.rxbus.thread.EventThread;
//
///**
// * 机器人管理实现类，使用RxBus接受外部消息
// *
// */
//public class RobotViewManager implements IRobotManager {
//    private final Context context;
//    private RobotView view;
//
//    public RobotViewManager(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public View createRobotView(View.OnClickListener onClickListener) {
//        view = new RobotView(context);
//        if (null != onClickListener) {
//            view.setRobotViewCLickListener(onClickListener);
//        }
//        return view;
//    }
//
//    @Override
//    public void showToasts(ToastMessage message) {
//        view.updateToast(message.getToastItems());
//    }
//
//    @Override
//    public void dismissToast() {
//        view.removeToast();
//    }
//
//    @Override
//    public void registerMessage() {
//        RxBus.get().register(this);
//    }
//
//    @Override
//    public void unRegisterMessage() {
//        dismissToast();
//        RxBus.get().unregister(this);
//    }
//
//    @Override
//    @Subscribe(
//            thread = EventThread.MAIN_THREAD,
//            tags = {@Tag(ToastMessage.TAG_TOAST)}
//    )
//    public void handleMessage(ToastMessage message) {
//        if (null == message || !message.isShow()) {
//            dismissToast();
//            return;
//        }
//        showToasts(message);
//    }
//
//    @Subscribe
//    public void onAssistantEvent(AssistantEvent event) {
//        if (!event.isShow()) {
//            view.playRise();
//        }
//    }
//
//    /**
//     * 被动创建备忘录消息提醒：机器人动画事件
//     */
//    @Subscribe
//    public void onPassiveCreateMemoMessageNotifyEvent(PassiveCreateMemoMessageNotifyEvent event){
//        if(event.isShow){
//            view.playNotify();
//        }
//    }
//}
