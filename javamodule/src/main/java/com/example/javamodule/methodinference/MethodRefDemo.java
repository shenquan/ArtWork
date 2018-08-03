package com.example.javamodule.methodinference;

/**
 * Created by hanshenquan.
 */
public class MethodRefDemo {
    public static String ops(StringFunc sf, String s) {
        return sf.func(s);
    }

    public static void main(String[] args) {
        String originalString = "You are well";
        String reverseString = ops(MyStringOps::strReverse, originalString);
        System.out.println("originalString=" + originalString + "\n");
        System.out.println("reverseString=" + reverseString);

    }
}
