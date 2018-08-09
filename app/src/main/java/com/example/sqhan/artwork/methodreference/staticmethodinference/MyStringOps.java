package com.example.sqhan.artwork.methodreference.staticmethodinference;

/**
 * Created by hanshenquan.
 */
public class MyStringOps {
    //静态方法： 反转字符串
    public static String strReverse(String str) {
        String result = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }
}
