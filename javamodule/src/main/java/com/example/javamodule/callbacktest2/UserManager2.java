package com.example.javamodule.callbacktest2;

/**
 * Created by hanshenquan.
 */
public class UserManager2 {

    public void executeRequest(int x, Callback callback) {
        if (x > 0) {
            callback.onSuccess();
        } else {
            callback.onFail(-1, "fail");
        }
    }

    interface Callback {
        void onSuccess();

        void onFail(int code, String message);
    }
}
