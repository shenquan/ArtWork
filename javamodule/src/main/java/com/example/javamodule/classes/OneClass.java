package com.example.javamodule.classes;

import com.example.javamodule.abstracts.AbOne;

import java.math.BigDecimal;


public class OneClass extends AbOne {
    public static void main(String[] args) {
//        System.out.println(AbOne.i);
//        System.out.println(IOne.i);
//        System.out.println(AbTwo.j);
//        int a[] = new int[4];
//        System.out.println(a[1]);

        Float af = 12f;
        System.out.println(new java.text.DecimalFormat("#").format(af));
        //使用这个
        System.out.println(new BigDecimal(String.valueOf(af)).stripTrailingZeros().toPlainString());
        System.out.println("fun1=" + fun1());
        System.out.println("fun1=" + fun1());
        System.out.println("fun2().x=" + fun2().x);


    }

    private static Integer fun1() {
        Integer i = 10000;

        try {
            i = 20000;
            return i;
        } catch (Exception e) {

        } finally {
            i = 30000;
            Integer f = 0;
//            return i;
        }
        return 40000;
    }

    private static A fun2() {
        A a = new A();
        A b = new A();
        A c = new A();
        a.x = 1;
        b.x = 2;
        c.x = 3;

        try {
//            a.x = 2;
            a = b;
            return a;
        } catch (Exception e) {

        } finally {
//            a.x = 3;
            a = c;
            int f = 0;
        }
        return a;
    }

    private static boolean fun() {
        for (int i = 0; i < 10; i++) {
            if (i == 1) {
                return true;
            }
        }
        int j = 0;
        return false;
    }

    private static class A {
        int x = 5;
    }
}
