package com.example.sqhan.artwork.annotation.test4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanshenquan.
 * 反射注解Field字段注入值
 * 源代码来自：https://blog.csdn.net/yhqbsand/article/details/38589003
 */
public class Main {
    public static void main(String[] args) {
        MyClassA a = new MyClassA();
        a.getV();

        List<String> x1 = new ArrayList<>();
        x1.add("1");
        x1.add("2");

        List<String> x2 = new ArrayList<>();
        x2.add("3");
        x2.add("4");
        x2.add("5");

//        x1.clear();
        x1.addAll(x2); // [1, 2, 3, 4, 5]
//        x1.addAll(0, x2); // [3, 4, 5, 1, 2]
//        x1.addAll(1, x2); // [1, 3, 4, 5, 2]

        System.out.println(x1.toString());
    }

    private static class MyClass {
        public MyClass() {

        }

        public void getV() {
            System.out.println("success");
        }
    }

    private static class MyClassA {

        @DataApi(value1 = MyClass.class)
        private MyClass class1;

        public MyClassA() {
            DataUtil.initDataManage(this);
        }

        public void getV() {
            System.out.println("successA");
            class1.getV();
        }
    }
}
