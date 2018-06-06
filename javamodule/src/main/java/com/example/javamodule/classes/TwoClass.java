package com.example.javamodule.classes;


import com.example.javamodule.abstracts.AbTwo;

public class TwoClass extends AbTwo {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        swapInt(a, b);
        System.out.println("a = " + a + " b = " + b);//无法实现交换

        String str1 = "1";
        String str2 = "2";
        swapString(str1, str2);
        System.out.println("str1 = " + str1 + " str2 = " + str2);//无法实现交换,String在这里相当于基本数据类型

        A obj1 = new A(1);
        A obj2 = new A(2);
        swapObj(obj1, obj2);
//        A temp = obj1;//只有这样才可以
//        obj1 = obj2;
//        obj2 = temp;
        System.out.println("obj1.a = " + obj1.a + " obj2.a = " + obj2.a);//无法实现交换

    }

    private static void swapInt(Integer a, Integer b) {
        Integer temp = a;
        a = b;
        b = temp;
        a = 5;//没有改变b的值
    }

    private static void swapString(String s1, String s2) {
        String temp = s1;
        s1 = s2;
        s2 = temp;
        s1 = "3";//没有改变b的值
    }

    private static void swapObj(A a, A b) {
        A temp = a;
        a = b;
        b = temp;
        a.a = 3;//改变了b对象的值
    }

    private static class A {
        public int a = 0;

        public A(int a) {
            this.a = a;
        }
    }

}
