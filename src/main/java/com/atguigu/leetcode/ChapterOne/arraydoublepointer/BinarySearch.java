package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/20 21:18
 */
public class BinarySearch {
    @Test
    public void test() {
        int[] arr = { 1, 8, 10, 89,1000,1000, 1234 };
        System.out.println(Arrays.toString(searchRange(arr, 1000)));
    }
    public int[] searchRange(int[] nums, int target) {
        return new int[]{leftBound(nums, target), rightBound(nums,
                target)};
    }

    private int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1; // 注意

        while (left <= right) {
            System.out.println("二分查找次数~~");
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1; // 注意
            } else if (nums[mid] > target) {
                right = mid - 1; // 注意
            }
        }
        return -1;
    }

    private int leftBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        // 搜索区间为 [left, right] 所以 为 <=
        while (left <= right) {
            System.out.println("二分查找次数~~");
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 搜索区间变为 [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // 搜索区间变为 [left, mid-1]
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 不断向左收缩，达到锁定左侧边界
                right = mid - 1;
            }
        }
        // 检查出界情况
        if (left >= nums.length || nums[left] != target) {
            return -1;
        }
        return left;
    }

    private int rightBound(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        //搜索区间[left,right] 退出条件为left > right 此时区间里没有元素.
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 不断向右收缩, 以锁定有边界.
                left = mid + 1; // 注意
            } else if (nums[mid] < target) {
                // [middle+ 1,right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // [left,mid-1]
                right = mid - 1;
            }
        }
        if (right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
    }
}

