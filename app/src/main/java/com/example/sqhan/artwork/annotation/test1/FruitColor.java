package com.example.sqhan.artwork.annotation.test1;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hanshenquan.
 * 水果颜色注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色属性
     */
    Color fruitColor() default Color.GREEN;

    /**
     * 颜色枚举
     */
    public enum Color {
        BLUE, RED, GREEN
    }
}
