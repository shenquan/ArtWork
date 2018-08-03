package com.example.javamodule.methodinference;

/**
 * Created by hanshenquan.
 */
public class MyStringOps {
    //静态方法： 反转字符串
    public static String strReverse(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            result.append(str.charAt(i));
        }
        return result.toString();
    }
}
