package com.example.sqhan.artwork.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by sqhan on 2017/10/11.
 */

public class DetailScrollView extends ScrollView {
    private OnMyScollChangedListener listener;

    public DetailScrollView(Context context) {
        super(context);
    }

    public DetailScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScollChangedListener(OnMyScollChangedListener listener) {
        this.listener = listener;
    }

    public interface OnMyScollChangedListener {
        void myScrollChanged(int dy);
    }

    /*@Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (listener != null) {
            listener.myScrollChanged(getScrollY());
        }
    }*/

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.myScrollChanged(getScrollY());
        }
    }
}
