package com.example.sqhan.artwork.enumtest;

/**
 * Created by hanshenquan.
 */
public enum EnumTest {
    One("手机", 1), Two("地址", 2);

    private String value;
    private int tagNum;

    EnumTest(String value, int tagNum) {
        this.value = value;
        this.tagNum = tagNum;
    }

    public static void main(String[] args) {
        System.out.println(One.toString());
        System.out.println(One.value);
        EnumTest test = EnumTest.One;
        System.out.println(test.name());
        System.out.println(test.value);
        System.out.println(test.tagNum);

        EnumTest test2 = EnumTest.Two;
        switch (test2) {
            case One:
                System.out.println(One.toString());
                break;
            case Two:
                System.out.println(Two.toString());
                break;
            default:
                break;
        }
    }
}
