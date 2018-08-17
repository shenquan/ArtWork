package com.example.sqhan.artwork.annotation.test2;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hanshenquan.
 */
public class StateTest {
    public static final int STATE_NONE = -1;
    public static final int STATE_LOADING = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;

    private @State
    int state;

    public static void main(String[] args) {
        StateTest stateTest = new StateTest();
//        stateTest.setState(5); // 会提示出错
        stateTest.setState(STATE_ERROR);
        System.out.println(stateTest.getState());
    }

    @State
    public int getState() {
        return state;
    }

    //此处参数加@state可以限制传入的参数必须为@IntDef所命名的
    public void setState(@State int state) {
        this.state = state;
    }

    @IntDef({STATE_EMPTY, STATE_ERROR, STATE_LOADING, STATE_NONE, STATE_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {

    }


}
