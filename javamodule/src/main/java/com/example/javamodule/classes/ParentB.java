package com.example.javamodule.classes;

public class ParentB extends SubA {
    final Integer i = 1;
    final String j = "1";

    private void fun() {
        Inner A = new Inner();
        System.out.println("A2");
    }

    public static void main(String[] args) {
        ParentB parentB = new ParentB();
        Inner inner = parentB.new Inner();
        inner.print();

//        parentB.i = new Integer(2);//can't
//        parentB.i=3;//can't
//        parentB.j="4";//can't
        Object object = new Object();
        parentB.fun();
    }
}
