package com.atguigu.leetcode.ChapterOne.prefixsum;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/10 11:56
 */
public class PrefixSumTest {
    /**
     * 给你输⼊⼀个整数数组 nums，请你实现 NumArray 类：
     * 1、NumArray(int[] nums) 使⽤数组 nums 初始化对象
     * 2、int sumRange(int i, int j) 返回数组 nums 从索引 i 到 j（i ≤ j）范围内元素的总和，包含
     * i，j 两点（也就是 sum(nums[i], nums[i + 1], ... , nums[j])）
     * 示例：
     * 输⼊：
     * ["NumArray", "sumRange", "sumRange", "sumRange"]
     * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
     * 输出：
     * [null, 1, -1, -3]
     */
    private class NumArray {
        private int[] prefixSum;

        public NumArray(int[] nums) {
            prefixSum = new int[nums.length + 1];
            prefixSum[0] = 0;
            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }


        // nums[1~3] 即求前4个元素前缀 与前1个元素前缀和的差.
        public int sumRange(int i, int j) {
            return prefixSum[j + 1] - prefixSum[i];
        }
    }

    @Test
    public void testNumArray() {
        int[] nums = {-2, 0, 3, -5, 2, -1};
        NumArray numArray = new NumArray(nums);
        int sumOne = numArray.sumRange(0, 2);
        int sumTwo = numArray.sumRange(2, 5);
        int sumThree = numArray.sumRange(0, 5);
        System.out.println("sumOne: " + sumOne + " sumTwo: " + sumTwo + " sumThree: " + sumThree);
    }

    /**
     * 给定⼀个⼆维矩阵 matrix，其中的⼀个⼦矩阵⽤其左上⻆坐标 (row1, col1) 和右下⻆坐标 (row2,
     * col2) 来表示。
     * 请你实现 NumMatrix 类：
     * 1、NumMatrix(int[][] matrix) 给定整数矩阵 matrix 进⾏初始化
     * 2、int sumRegion(int row1, int col1, int row2, int col2) 返回左上⻆ (row1, col1) ，
     * 右下⻆ (row2, col2) 所描述的⼦矩阵的元素总和。
     */
    private static class NumMatrix {
        private int[][] matrixPrefixSum;

        public NumMatrix(int[][] matrix) {
            // m行
            int m = matrix.length;
            // n列
            int n = matrix[0].length;
            matrixPrefixSum = new int[m + 1][n + 1];
            // 第一行置为0
            for (int j = 0; j < n; j++) {
                matrixPrefixSum[0][j] = 0;
            }
            // 第一列置为0
            for (int i = 0; i < m; i++) {
                matrixPrefixSum[i][0] = 0;
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    matrixPrefixSum[i + 1][j + 1] =
                            matrixPrefixSum[i][j + 1] + matrixPrefixSum[i + 1][j] + matrix[i][j] - matrixPrefixSum[i][j];
                }
            }
        }

        /**
         * sumRange
         *
         * @param row1 左上角的row1
         * @param col1 左上角的col1
         * @param row2 右下角的row2
         * @param col2 右下角的col2
         * @return 左上角与右下角形成的矩阵的数字和.
         */
        public int sumRange(int row1, int col1, int row2, int col2) {
            return matrixPrefixSum[row2 + 1][col2 + 1] - matrixPrefixSum[row1][col2 + 1] - matrixPrefixSum[row2 + 1][col1] + matrixPrefixSum[row1][col1];
        }
    }

    @Test
    public void testNumMatrix() {
        int[][] nums = {{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}};
        NumMatrix numMatrix = new NumMatrix(nums);
        int sumOne = numMatrix.sumRange(2, 1, 4, 3);
        int sumTwo = numMatrix.sumRange(1, 1, 2, 2);
        int sumThree = numMatrix.sumRange(1, 2, 2, 4);
        System.out.println("sumOne: " + sumOne + " sumTwo: " + sumTwo + " sumThree: " + sumThree);
    }


    private static class SubArrayPrefixSum {
        // private int[] prefixSum;
        //
        // public SubArrayPrefixSum(int[] nums) {
        //     prefixSum = new int[nums.length + 1];
        //     prefixSum[0] = 0;
        //     for (int i = 0; i < nums.length; i++) {
        //         prefixSum[i + 1] = nums[i] + prefixSum[i];
        //     }
        // }

        /**
         * 给你⼀个整数数组 nums 和⼀个整数 k，请你统计并返回该数组中和为 k 的连续⼦数组的个数。
         * 示例 1：
         * 输⼊：nums = [1,1,1], k = 2
         * 输出：2
         */
        public int findSubArrayNumber(int[] nums, int k) {
            // 1.先求出前缀和.
            // 2.穷举所有的前缀和区间, 得到满足条件的结果
            int[] prefixSum = new int[nums.length + 1];
            prefixSum[0] = 0;
            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = nums[i] + prefixSum[i];
            }

            int res = 0;
            for (int i = 1; i < prefixSum.length; i++) {
                for (int j = 0; j < i; j++) {
                    // 在计算,有多少个j 使得preSum[i]和preSum[j]的差为k
                    if (prefixSum[i] - prefixSum[j] == k) {
                        res++;
                    }
                }
            }
            return res;
        }

        public int findSubArrayNumber2(int[] nums, int k) {
            Map<Integer, Integer> prefixSumMap = new HashMap<>();
            int res = 0;
            int sumZeroToI = 0;
            for (int i = 0; i < nums.length; i++) {
                sumZeroToI += nums[i];
                int sumZeroToJ = sumZeroToI - k;
                if (prefixSumMap.containsKey(sumZeroToJ)) {
                    res = res + prefixSumMap.get(sumZeroToJ);
                }
                prefixSumMap.put(sumZeroToI, prefixSumMap.getOrDefault(sumZeroToI, 0) + 1);
            }
            return res;
        }

    }

    @Test
    public void testFindSubArrayNumber() {
        //nums = [1,1,1], k = 2
        //int[] nums = {1, 1, 1};
        int[] nums = {3,5,2,-2,4,1};
        int k = 5;
        SubArrayPrefixSum subArrayPrefixSum = new SubArrayPrefixSum();
        //int subArrayNumber = subArrayPrefixSum.findSubArrayNumber(nums, k);
        int subArrayNumber = subArrayPrefixSum.findSubArrayNumber2(nums, k);
        System.out.println(subArrayNumber);
    }
}
