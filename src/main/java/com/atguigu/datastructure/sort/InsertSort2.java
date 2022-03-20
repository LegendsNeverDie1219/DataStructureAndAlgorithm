package com.atguigu.datastructure.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/19 18:26
 */
public class InsertSort2 {
    @Test
    public void test1() {
        int[] arr = {101, 34, 119, 1, -1, -2};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        insertSort(arr);

        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序:
     * 将序列分为有序表 和无序表 两部分,初始化状态: 有序表里面只有一个元素arr[0],无序表中有n-1个元素,
     * 第一大轮, 从无序表中取出 arr[1], 然后在有序表中 找一个合适的位置, 插入
     * 第2大轮, 从无序表中取出 arr[2], 然后在有序表中 找一个合适的位置, 插入
     * 第i大轮, 从无序表中取出 arr[i], 然后在有序表中 找一个合适的位置, 插入
     * 第arr.length -1大轮, 从无序表中取出 arr[arr.length -1], 然后在有序表中 找一个合适的位置, 插入
     */
    public void insertSort(int[] arr) {
        for (int round = 1; round < arr.length; round++) {
            int insertValue = arr[round];
            // 有序表的大小为round , 有序表中最后一个元素的索引为round-1
            // [0,round-1]范围的值 与 insertValue 进行比较,
            // 如果insertValue 大或者相等, 则说明 orderedListIndex +1的位置就是合适的插入位置
            // 如果arr[orderedListIndex]大, 则需要当前有序列表的元素后移一位,index指针前移一位,继续判断.
            int orderedListIndex = round - 1;
            while (orderedListIndex >= 0 && arr[orderedListIndex] > insertValue) {
                arr[orderedListIndex + 1] = arr[orderedListIndex];
                orderedListIndex--;
            }

            // for (; orderedListIndex >= 0; orderedListIndex--) {
            //     // 找到了合适的插入位置
            //     if (arr[orderedListIndex] <= insertValue) {
            //         break;
            //     }
            //     // 当前有序列表的元素后移一位
            //     arr[orderedListIndex + 1] = arr[orderedListIndex];
            // }

            if (orderedListIndex + 1 != round) {
                arr[orderedListIndex + 1] = insertValue;
            }
        }
    }
}
