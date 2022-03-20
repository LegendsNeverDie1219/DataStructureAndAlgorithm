package com.atguigu.datastructure.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/19 23:01
 */
public class ShellSort2 {
    /**
     * 希尔排序, 即缩小步长版本的插入排序
     * 第一大轮 是按照 gap = length/2的方式, 进行分组
     * 然后,从第gap 个元素开始,依次对不同组的元素 进行简单插入排序
     * <p>
     * 第2大轮,按照 gap = ...
     * <p>
     * 直到 步长 == 0 结束
     */

    @Test
    public void test1() {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        // 第一大轮 分为 10/2 = 5组, 步长为5, 从第6个元素开始进行简单插入排序(第一组的 第二个元素)
        for (int i = 10 / 2; i < arr.length; i++) {
            int insertValue = arr[i];
            int orderedListIndex = i - 5;
            // 有序表中某一个元素的值 大于  待插入元素的值
            while (orderedListIndex >= 0 && arr[orderedListIndex] > insertValue) {
                // 如果arr[orderedListIndex]大, 则需要当前有序列表的元素后移一位,index指针前移一位,继续判断.
                arr[orderedListIndex + 5] = arr[orderedListIndex]; // todo 注意是有序列表元素后移.
                orderedListIndex -= 5;
            }
            arr[orderedListIndex + 5] = insertValue;
        }
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));

        for (int i = 5 / 2; i < arr.length; i++) {
            int insertValue = arr[i];
            int orderedListIndex = i - 2;
            // 有序表中某一个元素的值 大于  待插入元素的值
            while (orderedListIndex >= 0 && arr[orderedListIndex] > insertValue) {
                // 如果arr[orderedListIndex]大, 则需要当前有序列表的元素后移一位,index指针前移一位,继续判断.
                arr[orderedListIndex + 2] = arr[orderedListIndex];
                orderedListIndex -= 2;
            }
            arr[orderedListIndex + 2] = insertValue;
        }
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));

        for (int i = 2 / 2; i < arr.length; i++) {
            int insertValue = arr[i];
            int orderedListIndex = i - 1;
            while (orderedListIndex >= 0 && arr[orderedListIndex] > insertValue) {
                // 有序列表中指针 指向的元素后移
                arr[orderedListIndex + 1] = arr[orderedListIndex];
                orderedListIndex -= 1;
            }
            arr[orderedListIndex + 1] = insertValue;
        }
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));
    }

    public void shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int insertValue = arr[i];
                int orderedListIndex = i - gap;
                // 有序表中某一个元素的值 大于  待插入元素的值
                while (orderedListIndex >= 0 && arr[orderedListIndex] > insertValue) {
                    // 如果arr[orderedListIndex]大, 则需要当前有序列表的元素后移一位,index指针前移一位,继续判断.
                    arr[orderedListIndex + gap] = arr[orderedListIndex]; // todo 注意是有序列表元素后移.
                    orderedListIndex -= gap;
                }
                arr[orderedListIndex + gap] = insertValue;
            }
        }
    }

    @Test
    public void test2() {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        shellSort(arr);
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));

    }
}

