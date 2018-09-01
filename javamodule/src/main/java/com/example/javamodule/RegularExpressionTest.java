package com.example.javamodule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hanshenquan.
 */
public class RegularExpressionTest {
    public static void main(String[]args){
        String str = "方法41040319900223575x额";
        System.out.println(validator(str));
        //需要捕获异常
        try {
            System.out.println(str.substring(0, 6));
            System.out.println(str.substring(str.length() - 6, str.length()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 我国公民的身份证号码特点如下
     * 1.长度18位
     * 2.第1-17号只能为数字
     * 3.第18位只能是数字或者x
     * 4.第7-14位表示特有人的年月日信息
     * 请实现身份证号码合法性判断的函数，函数返回值：
     * 1.如果身份证合法返回1
     * 2.如果身份证长度不合法返回0
     *
     * @since 0.0.1
     */
    private static int validator(String str) {
//        把^与$去掉即可检测字符串中是否包含，否则只是单纯的检测身份证号
        //^ 匹配行的开始位置，$ 匹配行的结束位置
//        String regEx = "^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$";
        String regEx = "[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println("匹配成功:" + matcher.group() + "，在位置：" + matcher.start() + "-" + matcher.end());
            return 1;
        } else {
            return 0;
        }
    }

}
