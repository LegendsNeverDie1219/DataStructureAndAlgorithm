package com.atguigu.datastructure.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/19 17:46
 */
public class SelectSort2 {
    public static void main(String[] args) {
        int[] array = {101, 34, 119, 1, -1, 90, 123};
        System.out.println("排序前");
        System.out.println(Arrays.toString(array));


        int minValue = array[0];
        int minIndex = 0;
        for (int i = 0 + 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }
        if (minIndex != 0) {
            int temp = array[0];
            array[0] = array[minIndex];
            array[minIndex] = temp;
        }
        System.out.println("排序前");
        System.out.println(Arrays.toString(array));


        minValue = array[1];
        minIndex = 1;
        for (int i = 0 + 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }
        if (minIndex != 1) {
            int temp = array[1];
            array[1] = array[minIndex];
            array[minIndex] = temp;
        }
        System.out.println("排序前");
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void testSpeed() {
        int[] array = {101, 34, 119, 1, -1, 90, 123};
        System.out.println("排序前");
        System.out.println(Arrays.toString(array));
        selectSort(array);
        System.out.println("排序前");
        System.out.println(Arrays.toString(array));
    }

    /**
     * 选择排序的思想:
     * 第1趟 从[0,arr.length-1]范围内选取最小值,然后与arr[0] 交换
     * 第2趟, 从[1,,arr.length-1] 范围内选选取最小值,然后与arr[1]交换
     * 第i趟, 从[i-1,arr.length-1] 范围内选择最小值,然后与arr[i-1]交换
     * 第arr.length-1趟, 从[arr.length-2,array.length-1] 范围内选择最小值,然后与arr[arr.length-2]交换.
     */
    public void selectSort(int[] array) {
        for (int round = 0; round < array.length - 1; round++) {
            int minValue = array[round];
            int minIndex = round;
            for (int i = round; i < array.length; i++) {
                if (array[i] < minValue) {
                    minValue = array[i];
                    minIndex = i;
                }
            }
            if (minIndex != round) {
                array[minIndex] = array[round];
                array[round] = minValue;
            }
        }
    }
}
