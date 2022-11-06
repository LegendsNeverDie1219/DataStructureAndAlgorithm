package com.atguigu.leetcode.ChapterThree;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/31 7:19
 */
public class BackTrack5 {
    /**
     * 给定⼀个整数数组 nums 和⼀个正整数 k，找出是否有可能把这个数组分成 k 个⾮空⼦集，其总和都相等。
     * 输⼊：nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * 输出：True
     * 说明：有可能将其分成 4 个⼦集 {5}, {1,4}, {2,3}, {2,3} 等于总和。
     * <p>
     * 以数字的视角进行选择,
     * 遍历每个数字,  每个数字从 bucket[0~k-1]选择一个桶,装入,
     */
    @Test
    public void testCanPartitionKSubsets1() {
        int[] nums = {4, 3, 2, 3, 5, 2, 1};
        int k = 4;
        // int[] bucket = new int[k];
        // numberBackTrack(nums,0,bucket);

        // boolean result = canPartitionKSubsets1(nums, k);
        // System.out.println(result);


        boolean result = canPartitionKSubsets2(nums, k);
        System.out.println(result);
    }

    // begin=========================
    public void numberView() {
        int[] nums = {4, 3, 2, 3, 5, 2, 1};
        int k = 4;
        int[] bucket = new int[k];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < bucket.length; j++) {
                // 做选择
                bucket[j] += nums[i];
                //
                // ....
                // 撤销选择
                bucket[j] -= nums[i];
            }
        }
    }

    public void numberBackTrack(int[] nums, int index, int[] bucket) {
        if (index == nums.length) {
            return;
        }
        for (int j = 0; j < bucket.length; j++) {
            bucket[j] += nums[index];

            numberBackTrack(nums, index + 1, bucket);

            bucket[j] -= nums[index];
        }
    }
    // end=========================

    boolean canPartitionKSubsets1(int[] nums, int k) {
        // 入参校验
        if (k > nums.length) {
            return false;
        }

        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) {
            return false;
        }

        int targetSum = sum / k;
        int[] bucket = new int[k];
        Arrays.sort(nums);
        // 进行 num.length-1 大轮比较.
        for (int j = 1; j <= nums.length - 1; j++) {
            for (int i = 0; i < nums.length - j; i++) {
                // 相邻数字进行比较, 比较 0~num.length-2 次
                if (nums[i] < nums[i + 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                }
            }
        }
        // 选择: 下标为nums[index]的数字 装入哪一个桶中.bucket[0,k-1]
        return backTrackByNumberView(nums, 0, bucket, targetSum);
    }

    private boolean backTrackByNumberView(int[] nums, int index, int[] bucket, int targetSum) {
        // base case  所有的数字都执行完了选择.
        if (index == nums.length) {
            for (int actualSum : bucket) {
                if (actualSum != targetSum) {
                    return false;
                }
            }
            return true;
        }

        for (int j = 0; j < bucket.length; j++) {
            // 剪枝 如果装入该nums[index] 之后,超过目标容量, 则跳过
            // todo 为了尽可能多的命中该剪枝, 让nums 降序排序,先让大的数字做选择
            if (bucket[j] + nums[index] > targetSum) {
                continue;
            }
            // 将 nums[index] 装入 bucket[j]
            bucket[j] += nums[index];
            // todo 优化, 即如果找到一个可行的装载方案, 则返回. 阻止继续递归下去
            if (backTrackByNumberView(nums, index + 1, bucket, targetSum)) {
                return true;
            }

            bucket[j] -= nums[index];
        }
        return false;
    }

    /**
     * 给定⼀个整数数组 nums 和⼀个正整数 k，找出是否有可能把这个数组分成 k 个⾮空⼦集，其总和都相等。
     * 输⼊：nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * 输出：True
     * 说明：有可能将其分成 4 个⼦集 {5}, {1,4}, {2,3}, {2,3} 等于总和。
     * <p>
     * 以数字即
     */
    boolean canPartitionKSubsets2(int[] nums, int k) {
        if (k > nums.length) {
            return false;
        }

        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) {
            return false;
        }


        int targetSum = sum / k;
        int bucketSum = 0;
        boolean[] used = new boolean[nums.length];
        return backTrackByBucketView(k, bucketSum, nums, 0, used, targetSum);
    }

    private boolean backTrackByBucketView(int k, int bucketSum, int[] nums, int index, boolean[] used, int targetSum) {
        //base case
        if (bucketSum == targetSum) {
            // 说明第k号桶刚好已经装到预期值. 开始装第k-1号桶.
            //  让下一个桶从 nums[0] 开始选数字
           return backTrackByBucketView(k-1,0,nums,0,used,targetSum);
        }

        if (k == 0) {
            //说明 第1~k号桶都已经装到预期值, 返回结果.
            return true;
        }
        // 以桶的视角,遍历 nums[i~length]个数字,选择 装入nums[i],或者不装入nums[i]
        //  从 start 开始向后探查有效的 nums[i] 装入当前桶
        for (int i = index; i < nums.length; i++) {
            // 如果下标为i的数字已经装入了其他桶中,则跳过.
            if (used[i]) {
                continue;
            }
            if (bucketSum + nums[i] > targetSum) {
                continue;
            }

            bucketSum += nums[i];
            used[i] = true;

            // 遍历下一个数字, 桶执行装入/丢弃的选择
            if (backTrackByBucketView(k, bucketSum, nums, index + 1, used, targetSum)) {
                return true;
            }


            bucketSum -= nums[i];
            used[i] = false;
        }
        return false;
    }

    // === begin
    public void bucketView() {
        int[] nums = {4, 3, 2, 3, 5, 2, 1};
        int k = 4;
        int[] bucket = new int[k];
        boolean[] used = new boolean[nums.length];
        int sum = Arrays.stream(nums).sum();
        int targetSum = sum / k;

        while (k > 0) {
            if (bucket[k - 1] == targetSum) {
                k--;
            }
            for (int i = 0; i < nums.length; i++) {
                if (used[i] = true) {
                    continue;
                }
                // 做选择
                bucket[k] += nums[i];
                used[i] = true;
                // ...
                // 撤销选择
                bucket[k] -= nums[i];
                used[i] = false;
            }
        }
    }
    // == end

}
