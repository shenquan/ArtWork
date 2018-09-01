package com.example.javamodule.callbacktest2;

/**
 * Created by hanshenquan.
 */
public class User2 {

    public static void main(String[] args) {
        User2 user2 = new User2();
        UserManager2 userManager2 = new UserManager2();

        userManager2.executeRequest(-1, new UserManager2.Callback() {
            @Override
            public void onSuccess() {
                user2.fun1();
                System.out.println("onSuccess");
            }

            @Override
            public void onFail(int code, String message) {
                user2.fun2();
                System.out.println("code=" + code + ",message=" + message);
            }
        });
    }

    private void fun1() {
        System.out.println("fun1 onSuccess");
    }

    private void fun2() {
        System.out.println("fun2 onFail");
    }
}
