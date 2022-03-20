package com.atguigu.datastructure.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/20 18:15
 */
public class RadixSort2 {
    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));
        // 获取数组中最大数字的位数,进而确定要执行? 大轮循环.
        int maxValue = arr[0];
        for (int j : arr) {
            if (j > maxValue) {
                maxValue = j;
            }
        }
        int numberOfDigits = String.valueOf(maxValue).length();

        // 准备桶
        int[][] bucketArray = new int[10][arr.length];
        int[] bucketArrayValueCount = new int[10];

        // 第一大轮
        // 获取各个元素的个位数, 然后按照个位数 == 桶编号 分别将元素放入到 10个桶中,
        for (int i = 0; i < arr.length; i++) {
            int element = arr[i];
            int digit = element % 10;
            bucketArray[digit][bucketArrayValueCount[digit]] = element;
            bucketArrayValueCount[digit]++;
        }
        // 然后按照桶的编号大小, 依次取出各个元素,并放回到原来数组中
        int index = 0;
        for (int k = 0; k < 10; k++) {
            // 桶里面有元素
            if (bucketArrayValueCount[k] != 0) {
                // 取出编号为k的桶中数据
                for (int i = 0; i < bucketArrayValueCount[k]; i++) {
                    arr[index] = bucketArray[k][i];
                    index++;
                }
                // todo 清空该桶的计数
                bucketArrayValueCount[k] = 0;
            }
        }
        index = 0;
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));


        // 第2大轮
        // 获取各个元素的十位数, 然后按照十位数 == 桶编号 分别将元素放入到 10个桶中,
        for (int i = 0; i < arr.length; i++) {
            int element = arr[i];
            int digit = element / 10 % 10;
            bucketArray[digit][bucketArrayValueCount[digit]] = element;
            bucketArrayValueCount[digit]++;
        }
        // 然后按照桶的编号大小, 依次取出各个元素,并放回到原来数组中
        for (int k = 0; k < 10; k++) {
            // 桶里面有元素
            if (bucketArrayValueCount[k] != 0) {
                // 取出编号为k的桶中数据
                for (int i = 0; i < bucketArrayValueCount[k]; i++) {
                    arr[index] = bucketArray[k][i];
                    index++;
                }
                // todo 清空该桶的计数
                bucketArrayValueCount[k] = 0;
            }
        }
        index = 0;
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));


        // 第3大轮
        // 获取各个元素的百位数, 然后按照十位数 == 桶编号 分别将元素放入到 10个桶中,
        for (int i = 0; i < arr.length; i++) {
            int element = arr[i];
            int digit = element / 100 % 10;
            bucketArray[digit][bucketArrayValueCount[digit]] = element;
            bucketArrayValueCount[digit]++;
        }
        // 然后按照桶的编号大小, 依次取出各个元素,并放回到原来数组中
        for (int k = 0; k < 10; k++) {
            // 桶里面有元素
            if (bucketArrayValueCount[k] != 0) {
                // 取出编号为k的桶中数据
                for (int i = 0; i < bucketArrayValueCount[k]; i++) {
                    arr[index] = bucketArray[k][i];
                    index++;
                }
                // todo 清空该桶的计数
                bucketArrayValueCount[k] = 0;
            }
        }
        index = 0;
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 基数排序是一种分配式排序,又称为桶排序
     * 1. 先准备 10个桶(二维数组bucketArray[10][arr.length]),每个桶的容量为 arr.length
     * 桶编号为0-9,用来存放不同位数的众多元素.
     * 2.第一大轮排序时, 获取各个元素的个位数,
     * 然后按照个位数 == 桶编号 分别将元素放入到 10个桶中,
     * 然后按照桶的编号大小, 依次取出各个元素,并放回到原来数组中
     * 3.第2大轮排序时, 获取各个元素的十位数,
     * 然后按照个位数 == 桶编号 分别将元素放入到 10个桶中,
     * 然后按照桶的编号大小, 依次取出各个元素,并放回到原来数组中
     * 4....
     *
     * @param arr arr
     */
    public void radixSort(int[] arr) {
        // 获取数组中最大数字的位数,进而确定要执行? 大轮循环.
        int numberDigits = getNumberDigits(arr);
        // 准备桶
        int[][] bucketArray = new int[10][arr.length];
        int[] bucketArrayValueCount = new int[10];

        for (int round = 0, n = 1; round < numberDigits; round++, n *= 10) { //todo n的递增
            // 第一大轮
            // 获取各个元素的个位数, 然后按照个位数 == 桶编号 分别将元素放入到 10个桶中,
            for (int element : arr) {
                int digit = element / n % 10;
                bucketArray[digit][bucketArrayValueCount[digit]] = element;
                bucketArrayValueCount[digit]++;
            }
            // 然后按照桶的编号大小, 依次取出各个元素,并放回到原来数组中
            int index = 0;
            for (int k = 0; k < 10; k++) {
                // 桶里面有元素
                if (bucketArrayValueCount[k] != 0) {
                    // 取出编号为k的桶中数据
                    for (int i = 0; i < bucketArrayValueCount[k]; i++) {
                        arr[index] = bucketArray[k][i];
                        index++;
                    }
                    // todo 清空该桶的计数
                    bucketArrayValueCount[k] = 0;
                }
            }
        }
    }

    @Test
    public void test() {
        int[] arr = {53, 3, 542, 748, 14, 214};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        radixSort(arr);

        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));
    }

    private int getNumberDigits(int[] arr) {
        // 获取数组中最大数字的位数,进而确定要执行? 大轮循环.
        int maxValue = arr[0];
        for (int j : arr) {
            if (j > maxValue) {
                maxValue = j;
            }
        }
        return String.valueOf(maxValue).length();
    }
}
