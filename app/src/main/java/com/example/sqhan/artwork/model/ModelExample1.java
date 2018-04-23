package com.example.sqhan.artwork.model;

import java.io.Serializable;

/**
 * 成员变量声明为私有的，使用getter与setter，可以使用fastjson解析
 * 实现Serializable接口
 * Created by sqhan on 2018/4/23.
 */

public class ModelExample1 implements Serializable {
    private int a;
    private String str;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
