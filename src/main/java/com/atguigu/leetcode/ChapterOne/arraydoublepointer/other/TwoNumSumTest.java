package com.atguigu.leetcode.ChapterOne.arraydoublepointer.other;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/31 8:16
 */
public class TwoNumSumTest {
    @Test
    public void testTwoNumSum() {
        int[] arr = new int[]{3, 1, 3, 6};
        int target = 6;
        int[] numberSum = getNumberSumOne(arr, target);
        System.out.println(Arrays.toString(numberSum));
    }

    /**
     * 给你一个数组和一个整数target，可以保证数组中存在两个数的和为target，请你返回这两个数的索引。
     * nums = [3,1,3,6],target = 6
     * 方法一: 穷举 时间复杂度 O(N^2)
     */
    public int[] getNumberSumOne(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 方法二: 使用哈希表 建立 元素-> 元素的索引 的映射关系. 这样可以减少时间复杂度为O(N)
     * nums = [3,1,3,6],target = 6
     *
     * @param arr    目标数组
     * @param target 目标和
     * @return 结果索引数组
     */
    public int[] getNumberSumTwo(int[] arr, int target) {
        Map<Integer, Integer> elementIndexMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            elementIndexMap.put(arr[i], i);
        }
        for (int i = 0; i < arr.length; i++) {
            int otherElement = target - arr[i];
            // other 存在且不是它本身.
            if (elementIndexMap.containsKey(otherElement) && elementIndexMap.get(otherElement) != i) {
                return new int[]{elementIndexMap.get(otherElement), i};
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 使用双指针.
     *
     * @param nums   目标数组
     * @param target 目标和
     * @return 结果索引数组
     */
    public int[] getNumberSumThree(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) { // todo 注意.
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++; // 让 sum 大一点
            } else {
                right--; // 让 sum 小一点
            }
        }
        // 不存在这样两个数
        return new int[]{-1, -1};
    }

    private static class TwoSum {
        private List<Integer> nums = new ArrayList<>();
        private Set<Integer> sum = new HashSet<>();

        // [3,1,3,6],target = 6
        public void addElement(int newNumber) {
            // if (nums.isEmpty()) {
            //     sum.add(newNumber);
            // } else {
            //     for (int num : nums) {
            //         sum.add(num + newNumber);
            //     }
            // }
            for (int num : nums) {
                sum.add(newNumber + num);
            }
            nums.add(newNumber);
        }

        public boolean findTargetSum(int target) {
            if (sum.contains(target)) {
                return true;
            }
            return false;
        }
    }


}
