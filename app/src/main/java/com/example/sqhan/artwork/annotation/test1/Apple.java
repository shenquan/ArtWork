package com.example.sqhan.artwork.annotation.test1;

/**
 * Created by hanshenquan.
 * 这个例子注解Field实际没多大作用，看第4个例子注解Field用反射
 */
public class Apple {
    @FruitName("Apple")
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String appleColor;

    public static void main(String[] args) {
        Apple apple = new Apple();
        System.out.println(apple.getAppleName());
        System.out.println(apple.getAppleColor());

    }

    public String getAppleName() {
        return appleName;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }

    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }

    public void displayName() {
        System.out.println("水果的名字是：" + appleName);
    }
}
