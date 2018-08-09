package com.example.sqhan.artwork.methodreference.constructormethodreference;

/**
 * Created by hanshenquan.
 * 构造方法引用
 */
public class ConstructorRefDemo {
    public static void main(String[] args) {
        MyFunc1 myClassCons = MyClass::new;
        //相当于
        /*MyFunc1 myClassCons = new MyFunc1() {
            @Override
            public MyClass func(int n) {
                return new MyClass(n);
            }
        };*/
        MyClass myClassObject = myClassCons.func(100);

        System.out.println("val in mc is: " + myClassObject.getVal());
    }
}
