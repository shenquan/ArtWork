package com.example.sqhan.artwork.di.modle;

import javax.inject.Inject;

/**
 * Created by sqhan on 2018/5/9
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class Factory {
    public int j = 2;
    public Product product;

    @Inject
    public Factory(Product product) {
        this.product = product;
    }
}
