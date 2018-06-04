package com.example.javamodule.Algorithm;

import java.util.Arrays;

public class MyQuickSort {
    public static void sort(int[] arr, int low, int high) {
        int l = low;
        int h = high;
        int pivot = arr[low];
        while (l < h) {
            while (l < h && arr[h] >= pivot) h--;
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;
            }
            while (l < h && arr[l] <= pivot) l++;
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;
            }
        }
        if (l > low) sort(arr, low, l - 1);
        if (h < high) sort(arr, l + 1, high);
    }

    //值传递，不能交换
    private static void swap(String a, String b) {
        String temp = a;
        a = b;
        b = temp;
    }

    //值传递，不能改变
    private static void change(String c) {
        c = "4";
    }

    //引用传递，可以改变。引用传递本质上也是值传递，传递的是对象的地址
    private static void changeArr(int[] arr) {
        if (arr[1] != 0) {
            arr[1] = 2222;//测试
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        sort(arr, 0, arr.length - 1);
        System.out.println("排序结果：" + Arrays.toString(arr));
        String a = "1", b = "2";
        swap(a, b);
        System.out.println("a=" + a + " b=" + b);
        String c = "3";
        change(c);
        System.out.println("c=" + c);
        changeArr(arr);
        System.out.println("改变结果：" + Arrays.toString(arr));
        sort(arr, 0, arr.length - 1);
        System.out.println("排序结果：" + Arrays.toString(arr));
    }
}
