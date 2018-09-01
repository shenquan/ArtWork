package com.example.javamodule.callbacktest1;

/**
 * Created by hanshenquan.
 */
public interface Callback {
    void onSuccess();

    void onFail(int code,String message);
}
