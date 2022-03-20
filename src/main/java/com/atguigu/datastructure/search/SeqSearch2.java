package com.atguigu.datastructure.search;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/20 21:11
 */
public class SeqSearch2 {
    public static void main(String[] args) {
        // 数组无序.
        int[] arr = new int[]{1,9,-11,-1, 34,89};
        int findValue = -11;
        int index = seqSearch(arr,findValue);
        if (index == -1) {
            System.out.println("没有找到");
        }  else {
            System.out.println("找到了,下标为: " + index);
        }
    }
    // 线性查找
    private static int seqSearch(int[] arr, int findValue) {
        for (int i = 0; i < arr.length; i++) {
            if (findValue == arr[i]) {
                return i;
            }
        }
        return -1;
    }
}
