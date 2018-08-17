package com.example.sqhan.artwork.annotation.test4;

import java.lang.reflect.Field;

/**
 * Created by hanshenquan.
 * 反射注解Field字段注入值
 */
public class DataUtil {
    public static void initDataManage(Object object) {
        setProxy(object, object.getClass());
    }

    private static void setProxy(Object object, Class<?> clazz) {
        if (clazz == null || object == null)
            return;

        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                DataApi dataApi = field.getAnnotation(DataApi.class);
                if (dataApi != null) {
                    Class<?> dataApiClass = dataApi.value1();
                    try {
                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(object, dataApiClass.newInstance());//这个地方 可以换成接口，用动态代理生成相关的实现类
                        field.setAccessible(accessible);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        setProxy(object, clazz.getSuperclass());
    }

}
