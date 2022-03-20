package com.atguigu.datastructure.sort;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/19 16:59
 */
public class BubbleSort2 {
    public static void main(String[] args) {
        int[] array = {3, 9, -1, 10, 20};
        System.out.println("排序前");
        System.out.println(Arrays.toString(array));

        System.out.println("排序后");
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
        // System.out.println("排序后");
        // // 模拟第一趟排序
        // for (int i = 0; i < array.length - 1 - 0; i++) {
        //     if (array[i] > array[i + 1]) {
        //         int temp = array[i];
        //         array[i] = array[i + 1];
        //         array[i + 1] = temp;
        //     }
        // }
        // System.out.println(Arrays.toString(array));
        //
        //
        // // 模拟第二趟排序
        // System.out.println("排序后");
        // for (int i = 0; i < array.length - 1 -1; i++) {
        //     if (array[i] > array[i + 1]) {
        //         int temp = array[i];
        //         array[i] = array[i + 1];
        //         array[i + 1] = temp;
        //     }
        // }
        // System.out.println(Arrays.toString(array));
    }

    @Test
    public void testBubbleSortSpeed() {
        int size = 80000;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            // [0,80000)
            array[i] = (int) (Math.random() * 80000);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTime = simpleDateFormat.format(new Date());
        System.out.println("排序前的时间是=" + startTime);

        bubbleSort(array);

        String endTime = simpleDateFormat.format(new Date());
        System.out.println("排序后的时间是=" + endTime);
    }

    /**
     * 冒泡排序的思想:
     * 从左到右遍历序列, 进行相邻两个元素的比较,如果逆序,则交换位置.
     * 一共要进行array.length -1 趟遍历.
     * 第1趟 找到了最大的元素,并放到了 数组的倒数第1个位置
     * 第2趟 找到了次打的元素,并放到了 数组的倒数第2个位置
     * 第array.length-1 趟, 找到了最小的元素, 并放到了 数组的第一个位置
     * <p>
     * 优化:
     * 如果发现在某一趟中 一次交换都没有发生,则说明已经有序, 可以退出循环了
     */
    public static void bubbleSort(int[] array) {
        int temp = 0;
        // 表示是否进行了交换
        boolean flag = false;
        for (int round = 0; round < array.length - 1; round++) {
            for (int i = 0; i < array.length - 1 - round; i++) {
                if (array[i] > array[i + 1]) {
                    flag = true;
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }

            if (flag) {
                // 发生了交换,说明数组还不是有序的,仍然要进行下一趟
                flag = false;
            } else {
                break;
            }
        }
    }
}
