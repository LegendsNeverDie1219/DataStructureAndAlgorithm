package com.atguigu.Algorithm.binarysearchnorecursion;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/14 8:03
 */
public class BinarySearchNoRecur2 {
    // 二分查找算法(非递归)代码实现
    public static void main(String[] args) {
        int[] arr = new int[]{1, 3, 8, 10, 11, 67, 100};
        int target = 10;
        int result = binarySearchNoRecur(arr, target);
        if (result == -1) {
            System.out.println("没有找到");
        } else {
            System.out.println("目标在数组中的下标为: " + result);
        }
    }

    private static int binarySearchNoRecur(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
