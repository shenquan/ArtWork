package com.example.javamodule.callbacktest1;

/**
 * Created by hanshenquan.
 */
public class User implements Callback {

    public static void main(String[] args) {
        User user = new User();
        UserManager userManager = new UserManager();

        userManager.performRequest(1, user);
        userManager.performRequest(-1, user);
    }

    @Override
    public void onSuccess() {
        System.out.println("onSuccess");
    }

    @Override
    public void onFail(int code, String message) {
        System.out.println("code=" + code + ",message=" + message);
    }
}
