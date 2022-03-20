package com.atguigu.Algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/14 19:26
 */
public class MaxDivisorAndMinMultiplyTest {
    @Test
    public void testGetMaxCommonDivisor() {
        int[] arr1 = new int[]{12, 15, 30};
        int[] arr2 = new int[]{12, 30, 18};
        int[] arr3 = new int[]{12, 15, 17};
        System.out.println(getMaxCommonDivisor(arr1));
        System.out.println(getMaxCommonDivisor(arr2));
        System.out.println(getMaxCommonDivisor(arr3));
    }

    /**
     * 获取一个数组中所有数据的最大公约数
     *
     * @param arr arr
     * @return 最大公约数
     */
    public int getMaxCommonDivisor(int[] arr) {
        // 从小到大排序
        Arrays.sort(arr);
        int maxDivisor = 1;
        int minElementValue = arr[0];
        boolean canDivide = true;
        for (int i = minElementValue; i >= 2; i--) { //除数 : minElementValue~2
            for (int j = 0; j < arr.length; j++) { //被除数 arr[0]~arr[length-1]
                if (arr[j] % i != 0) {
                    canDivide = false;
                    break;
                }
            }
            if (canDivide) {
                maxDivisor = i;
                break;
            }
            // 重置标识位
            canDivide = true;
        }
        return maxDivisor;
    }

    @Test
    public void testGetMinCommonMultiply() {
        int[] arr1 = new int[]{12, 15, 30};
        int[] arr2 = new int[]{12, 30, 18};
        int[] arr3 = new int[]{12, 15, 17};
        System.out.println(getMinCommonMultiply(arr1));
        System.out.println(getMinCommonMultiply(arr2));
        System.out.println(getMinCommonMultiply(arr3));
    }

    public int getMinCommonMultiply(int[] arr) {
        List<Integer> sortedList =
                Arrays.stream(arr).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Integer maxElementValue = sortedList.get(0);
        int maxCommonMultiply = 1;
        for (int element : arr) {
            maxCommonMultiply *= element;
        }
        boolean canDivide = true;
        int minCommonMultiply = maxCommonMultiply;
        for (int i = maxElementValue; i < maxCommonMultiply; i++) { // 被除数 maxElementValue ~maxCommonMultiply
            for (int j = 0; j < arr.length; j++) { //除数 arr[0]~arr[length-1]
                if (i % arr[j] != 0) {
                    canDivide = false;
                    break;
                }
            }
            if (canDivide) {
                minCommonMultiply = i;
                break;
            }
            canDivide = true;
        }
        return minCommonMultiply;
    }
}
