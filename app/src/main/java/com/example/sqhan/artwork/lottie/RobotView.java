//package com.example.sqhan.artwork.lottie;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Rect;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.airbnb.lottie.LottieAnimationView;
//
///**
// * 机器人视图
// *
// */
//public class RobotView extends RelativeLayout {
//    /**
//     * 机器人
//     */
//    private LottieAnimationView robotView;
//
//    /**
//     * toast 容器
//     */
//    private LinearLayout toastContainer;
//
//    private DefaultRobotAnimManager mAnimManager = new DefaultRobotAnimManager();
//
//    public RobotView(Context context) {
//        super(context);
//        init();
//    }
//
//    public RobotView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public RobotView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        inflate(getContext(), R.layout.layout_ai_robot, this);
//        robotView = findViewById(R.id.robot);
//        toastContainer = findViewById(R.id.ll_toast_container);
//        mAnimManager.initView(robotView);
//        mAnimManager.playIdle(robotView);
//    }
//
//    /**
//     * 更新 toast
//     *
//     * @param toastItems toast 对应的数据
//     */
//    public void updateToast(List<ToastMessage.ToastItem> toastItems) {
//        // 避免重复创建子 view
//        if (toastContainer.getChildCount() != toastItems.size()) {
//            reCreateToast(toastItems);
//            return;
//        }
//        for (int i = 0; i < toastContainer.getChildCount(); i++) {
//            ToastMessage.ToastItem item = toastItems.get(i);
//            TextView toastView = (TextView) toastContainer.getChildAt(i);
//            toastView.setText(item.getToastText());
//            toastView.setOnClickListener(item.getToastClickListener());
//        }
//    }
//
//    /**
//     * 重新创建 toasts
//     *
//     * @param toastItems toast 对应的数据
//     */
//    public void reCreateToast(List<ToastMessage.ToastItem> toastItems) {
//        removeToast();
//        int margin = DensityUtil.dip2px(getContext(), 6f);
//        for (int i = 0; i < toastItems.size(); i++) {
//            ToastMessage.ToastItem item = toastItems.get(i);
//            TextView toastView = new TextView(getContext());
//            toastView.setText(item.getToastText());
//            toastView.setOnClickListener(item.getToastClickListener());
//            if (i == 0) {
//                toastView.setBackgroundResource(R.drawable.first_toast_oval_shape_bg);
//            } else {
//                toastView.setBackgroundResource(R.drawable.toast_oval_shape_bg);
//            }
//            toastView.setPadding(DensityUtil.dip2px(getContext(), 10f),
//                    DensityUtil.dip2px(getContext(), 4f),
//                    DensityUtil.dip2px(getContext(), 10f),
//                    DensityUtil.dip2px(getContext(), 4f));
//            toastView.setEllipsize(TextUtils.TruncateAt.END);
//            toastView.setGravity(Gravity.CENTER);
//            toastView.setSingleLine();
//            toastView.setTextColor(Color.parseColor("#333333"));
//            toastView.setTextSize(13);
//            toastContainer.addView(toastView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//
//            ((LinearLayout.LayoutParams) toastView.getLayoutParams()).rightMargin = margin;
//
//        }
//    }
//
//    /**
//     * 移出全部 toast
//     */
//    public void removeToast() {
//        toastContainer.removeAllViews();
//    }
//
//    /**
//     * 获得小机器人和气泡加在一起所占的矩形区域
//     *
//     * @return 小机器人和气泡加在一起所占的矩形区域
//     */
//    public Rect getContentRect() {
//        Rect robotRect = new Rect();
//        robotRect.left = getWidth() - robotView.getWidth() - toastContainer.getWidth();
//        robotRect.top = robotView.getTop();
//        robotRect.bottom = robotRect.top + robotView.getHeight();
//        robotRect.right = robotRect.left + robotView.getWidth() + toastContainer.getWidth();
//        return robotRect;
//    }
//
//    public void setRobotViewCLickListener(OnClickListener onClickListener) {
//        robotView.setOnClickListener(v -> {
//            mAnimManager.playDown(robotView, result -> onClickListener.onClick(v));
//        });
//    }
//
//    /**
//     * 机器人上升
//     */
//    public void playRise() {
//        mAnimManager.playRise(robotView, result -> mAnimManager.playIdle(robotView));
//    }
//
//    /**
//     * 被动创建备忘录消息提醒：机器人动画
//     */
//    public void playNotify() {
//        mAnimManager.playNotify(robotView, result -> mAnimManager.playIdle(robotView));
//    }
//
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        mAnimManager.playIdle(robotView);
//    }
//}
