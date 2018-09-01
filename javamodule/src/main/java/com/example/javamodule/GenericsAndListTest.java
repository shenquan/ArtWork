package com.example.javamodule;

import java.util.ArrayList;

/**
 * 泛型类测试
 * 正则匹配身份证号
 */

public class GenericsAndListTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        // 必须判断，否则会异常，get(0)即获取第一个元素
        if (!list.isEmpty()) {
            System.out.print("Hello World" + list.get(0));
        }

        try {
            exceptionTest(-1);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            System.out.println("出现了异常");

        }

        // 泛型测试类
        StrClass<Integer> strClass = new StrClass<>();
        strClass.x1("x1");
        System.out.println(strClass.x2());
        int y = 3;
        strClass.x3(y);

        String a1 = "111";
        String a2 = fun1(a1);
        System.out.println(a2);

    }

    private static void exceptionTest(int x) throws NumberFormatException {
        if (x < 0) {
            throw new NumberFormatException();
        } else {
            System.out.println("正常");
        }
    }

    /**
     * 前面的T时指明类型
     *
     * @param a
     * @param <T>
     * @return
     */
    private static <T> T fun1(T a) {
        return a;
    }

    public interface test<T> {
        void x1(T x);

        T x2();
    }

    // 必须为static，否则不能再static方法中调用
    private static class StrClass<P extends Integer> implements test<String> {

        @Override
        public void x1(String x) {
            System.out.println("x1=" + x);
        }

        @Override
        public String x2() {
            return "x2";
        }

        void x3(P p) {
            System.out.println("x3+" + p);
        }
    }
}
