package com.example.javamodule.callbacktest1;

/**
 * Created by hanshenquan.
 */
public class UserManager {

    public void performRequest(int x, Callback callback) {
        if (x > 0) {
            callback.onSuccess();
        } else {
            callback.onFail(-1, "fail");
        }
    }
}
