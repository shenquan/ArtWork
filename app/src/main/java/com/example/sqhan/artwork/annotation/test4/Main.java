package com.example.sqhan.artwork.annotation.test4;

/**
 * Created by hanshenquan.
 * 反射注解Field字段注入值
 * 源代码来自：https://blog.csdn.net/yhqbsand/article/details/38589003
 */
public class Main {
    public static void main(String[] args) {
        MyClassA a = new MyClassA();
        a.getV();
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
