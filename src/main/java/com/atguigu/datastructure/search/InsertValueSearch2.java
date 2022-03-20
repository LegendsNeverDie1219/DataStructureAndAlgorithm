package com.atguigu.datastructure.search;

import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/20 21:58
 */
public class InsertValueSearch2 {
    @Test
    public void test() {
        int[] arr = { 1, 8, 10, 89,1000,1000, 1234 };
        System.out.println(insertValueSearch(arr, 1234));
    }

    // 对于数据量较大,关键字分布均匀的查找表来说,采用插入排序好.
    private int insertValueSearch(int[] arr, int findValue) {
        int left = 0;
        int right = arr.length - 1;
        if (findValue < arr[left] || findValue > arr[right]) {
            return -1;
        }
        while (left <= right) {
            System.out.println("插值查找次数~~");
            //int middle = left + (right - left) / 2;
           int middle = left + (right - left) * (findValue - arr[left]) / (arr[right] - arr[left]);
            if (arr[middle] < findValue) {
                left = middle + 1;
            } else if (arr[middle] > findValue) {
                right = middle - 1;
            } else {
               return middle;
            }
        }
        return -1;
    }

}
