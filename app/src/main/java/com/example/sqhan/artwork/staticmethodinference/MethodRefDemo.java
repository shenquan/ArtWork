package com.example.sqhan.artwork.staticmethodinference;

/**
 * Created by hanshenquan.
 */
public class MethodRefDemo {
    public static String stringOp(StringFunc sf, String s) {
        return sf.func(s);
    }

    public static void main(String[] args) {
        String inStr = "lambda add power to Java";
        /**MyStringOps::strReverse 相当于实现了接口方法func()，
         并在接口方法func()中作了MyStringOps.strReverse()操作*/
//        String outStr = stringOp(MyStringOps::strReverse, inStr);
        //相当于
        String outStr = stringOp(new StringFunc() {
            @Override
            public String func(String n) {
                return MyStringOps.strReverse(n);
            }
        }, inStr);
        System.out.println("Original string: " + inStr);
        System.out.println("String reserved: " + outStr);
    }
}
