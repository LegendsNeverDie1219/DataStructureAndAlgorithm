package com.atguigu.leetcode.ChapterOne.arraydoublepointer.other;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/2 8:07
 */
public class ThreeFourNumSumTest {
    @Test
    public void test() {
        int[] nums = new int[]{5, 3, 1, 6};
        int target = 9;
        int[] twoNumberSum = getTwoNumberSum(nums, target);
        System.out.println(Arrays.toString(twoNumberSum));
    }

    /**
     * 如果假设输入一个数组 nums 和一个目标和 target，请你返回 nums 中能够凑出 target 的两个元素的值，
     * 比如输入 nums = [5,3,1,6], target = 9，那么算法返回两个元素 [3,6]。可以假设只有且仅有一对儿元素可以凑出 target。
     */
    public int[] getTwoNumberSum(int[] nums, int target) {
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                left++;
            } else if (nums[left] + nums[right] > target) {
                right--;
            } else {
                return new int[]{nums[left], nums[right]};
            }
        }
        return new int[]{};
    }

    @Test
    public void test2() {
        int[] nums = new int[]{1, 3, 1, 2, 2, 3};
        int target = 4;
        List<List<Integer>> list = getTwoNumberSum2(nums, target);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    /**
     * nums 中可能有多对儿元素之和都等于 target，请你的算法返回所有和为 target 的元素对儿，其中不能出现重复
     */
    public List<List<Integer>> getTwoNumberSum2(int[] arr, int target) {
        Arrays.sort(arr);

        int left = 0;
        int right = arr.length - 1;
        List<List<Integer>> res = new ArrayList<>();
        while (left < right) {
            int currentLeftNum = arr[left];
            int currentRightNum = arr[right];
            if (currentLeftNum + currentRightNum < target) {
                while (left < right && currentLeftNum == arr[left]) {
                    left++;
                }
            } else if (currentLeftNum + currentRightNum > target) {
                while (left < right && currentRightNum == arr[right]) {
                    right--;
                }
            } else {
                res.add(Lists.newArrayList(currentLeftNum, currentRightNum));
                while (left < right && currentLeftNum == arr[left]) {
                    left++;
                }
                while (left < right && currentRightNum == arr[right]) {
                    right--;
                }
            }
        }
        return res;
    }

    @Test
    public void testThreeNumberSum() {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        int target = 0;
        List<List<Integer>> threeNumberSum = getThreeNumberSum(nums, target);
        System.out.println(threeNumberSum);

    }

    /**
     * 三数字之和 例如: nums = {-1,0,1,2,-1,-4}  target = 0 满足条件的三元组为[[-1,0,1] [-1,-1,2]]
     *
     * @param nums   搜索数组
     * @param target 目标和
     * @return 返回符合条件的三元组
     */
    public List<List<Integer>> getThreeNumberSum(int[] nums, int target) {
        List<List<Integer>> resList = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            int firstNumber = nums[i];
            int theOtherTwoNumSum = target - firstNumber;
            List<List<Integer>> twoNumsRes = getTheOtherTwoNumSum(nums, theOtherTwoNumSum, i + 1);
            twoNumsRes.forEach(item -> {
                item.add(0, firstNumber);
                resList.add(item);
            });
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return resList;
    }

    public List<List<Integer>> getTheOtherTwoNumSum(int[] arr, int target, int start) {
        int left = start;
        int right = arr.length - 1;
        List<List<Integer>> res = new ArrayList<>();
        while (left < right) {
            int currentLeftNum = arr[left];
            int currentRightNum = arr[right];
            if (currentLeftNum + currentRightNum < target) {
                while (left < right && currentLeftNum == arr[left]) {
                    left++;
                }
            } else if (currentLeftNum + currentRightNum > target) {
                while (left < right && currentRightNum == arr[right]) {
                    right--;
                }
            } else {
                res.add(Lists.newArrayList(currentLeftNum, currentRightNum));
                while (left < right && currentLeftNum == arr[left]) {
                    left++;
                }
                while (left < right && currentRightNum == arr[right]) {
                    right--;
                }
            }
        }
        return res;
    }

    @Test
    public void testFourNumberSum() {
        int[] nums = {1, 0, -1, 0, -2, -2};
        int target = 0;
        List<List<Integer>> threeNumberSum = getFourNumberSum(nums, target);
        System.out.println(threeNumberSum);

    }

    /**
     * 四数字之和 例如: nums = {-1,0,1,2,-1,-4}  target = 0 满足条件的三元组为[[-1,0,1] [-1,-1,2]]
     *
     * @param nums   搜索数组
     * @param target 目标和
     * @return 返回符合条件的四元组
     */
    public List<List<Integer>> getFourNumberSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> resList = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int firstNumber = nums[i];
            int theOtherThreeNumSum = target - firstNumber;
            List<List<Integer>> threeNumsRes = getTheOtherThreeNumberSum(nums, theOtherThreeNumSum, i + 1);
            threeNumsRes.forEach(item -> {
                item.add(0, firstNumber);
                resList.add(item);
            });
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return resList;
    }

    public List<List<Integer>> getTheOtherThreeNumberSum(int[] nums, int target, int start) {
        List<List<Integer>> resList = new ArrayList<>();

        for (int i = start; i < nums.length; i++) {
            int firstNumber = nums[i];
            int theOtherTwoNumSum = target - firstNumber;
            List<List<Integer>> twoNumsRes = getTheOtherTwoNumSum(nums, theOtherTwoNumSum, i + 1);
            twoNumsRes.forEach(item -> {
                item.add(0, firstNumber);
                resList.add(item);
            });
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
        return resList;
    }

    @Test
    public void testFourNumberSum2() {
        int[] nums = {1, 0, -1, 0, -2, -2};
        int target = 0;
        Arrays.sort(nums);

        List<List<Integer>> fourNumberSum = nNumberSum(nums, target, 0,4);
        System.out.println(fourNumberSum);

    }

    /**
     * @param arr    目标数组
     * @param target 目标和
     * @param start  起始搜索索引
     * @param n      n 具体几个数之和
     * @return list
     */
    public List<List<Integer>> nNumberSum(int[] arr, int target, int start, int n) {
        if (n < 2 || n > arr.length) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        if (n == 2) {
            int left = start;
            int right = arr.length - 1;
            while (left < right) {
                int currentLeftNumber = arr[left];
                int currentRightNumber = arr[right];
                if (arr[left] + arr[right] < target) {
                    while (left < right && currentLeftNumber == arr[left]) {
                        left++;
                    }
                } else if (arr[left] + arr[right] > target) {
                    while (left < right && currentRightNumber == arr[right]) {
                        right--;
                    }
                } else {
                    res.add(Lists.newArrayList(arr[left], arr[right]));
                    // 第一次必然执行.
                    while (left < right && currentLeftNumber == arr[left]) {
                        left++;
                    }
                    // 第一次必然执行.
                    while (left < right && currentRightNumber == arr[right]) {
                        right--;
                    }
                }
            }
            return res;
        } else {
            for (int i = start; i < arr.length; i ++) {
                int number = arr[i];
                List<List<Integer>> theOtherSumList = nNumberSum(arr, target - arr[i], i + 1, n - 1);
                theOtherSumList.forEach(item -> {
                    item.add(number);
                    res.add(item);
                });
                while (i <  arr.length -1 && arr[i] == arr[i+1]) {
                    i++;
                }
            }
            return res;
        }
    }

}
