package com.atguigu.leetcode.ChapterThree;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/30 19:42
 */
public class BackTrack4 {
    private int count = 0;

    /**
     * 给你⼀个整数数组 nums 和⼀个整数 target，向数组中的每个整数前添加 '+' 或 '-'，然后串联起所有整
     * 数，可以构造⼀个表达式。
     * 例如，nums = [2, 1]，可以在 2 之前添加 '+'，在 1 之前添加 '-'，然后串联起来得到表达式 "+2-
     * 1"。
     * 返回可以通过上述⽅法构造的、运算结果等于 target 的不同表达式的数⽬。
     * 示例 1：
     * 输⼊：nums = [1,1,1,1,1], target = 3
     * 输出：5
     * 解释：⼀共有 5 种⽅法让最终⽬标和为 3。
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     */
    @Test
    public void testFindTargetSumWays() {
        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;

        // int targetSumWays = findTargetSumWays1(nums, target);
        // System.out.println(targetSumWays);

        // int targetSumWays = findTargetSumWays2(nums, target);
        // System.out.println(targetSumWays);

        int targetSumWays = findTargetSumWays3(nums, target);
        System.out.println(targetSumWays);

    }

    public int findTargetSumWays1(int[] nums, int targetSum) {
        // 选择宽度: 第i(i从0开始)个位置前面放置加号还是减号,即第i个位置是+ nums[i] or -nums[i]
        // 路径高度:  需要nums数组中的所有元素都参与
        // base case 触底条件 : 到达第nums.length个位置 ,正确条件: 用了nums中所有的数字,并且sum =targetSum.
        int start = 0;
        int currentSum = 0;

        backTrack4FindTargetSumWays1(nums, start, currentSum, targetSum);
        return count;
    }

    private void backTrack4FindTargetSumWays1(int[] nums, int start, int currentSum, int targetSum) {
        if (start == nums.length) {
            // 说明 : 0~nums.length-1 位置的元素都已经使用了
            if (currentSum == targetSum) {
                count++;
            }
            return;
        }

        // 第 i个位置为  +nums[i]
        currentSum += nums[start];
        backTrack4FindTargetSumWays1(nums, start + 1, currentSum, targetSum);
        currentSum -= nums[start];

        // 第i个位置为 -nums[i]
        currentSum -= nums[start];
        backTrack4FindTargetSumWays1(nums, start + 1, currentSum, targetSum);
        currentSum += nums[start];
    }

    public int findTargetSumWays2(int[] nums, int targetSum) {
        int start = 0;
        backTrack4FindTargetSumWays2(nums, start, targetSum);
        return count;
    }

    private void backTrack4FindTargetSumWays2(int[] nums, int index, int remainDifference) {
        if (nums.length == index) {
            if (remainDifference == 0) {
                count++;
            }
            return;
        }
        // 下标为start的位置选择 +nums[index]
        remainDifference -= nums[index];
        backTrack4FindTargetSumWays2(nums, index + 1, remainDifference);
        remainDifference += nums[index];

        // 下标为start的位置选择-nums[index]
        remainDifference += nums[index];
        backTrack4FindTargetSumWays2(nums, index + 1, remainDifference);
        remainDifference -= nums[index];
    }


    public int findTargetSumWays3(int[] nums, int targetSum) {
        int start = 0;
        // 加一个备忘录,进行剪枝
        Map<String, Integer> memory = new HashMap<>();
        return getCountOfRemainDiffIsZeroByNums(nums, start, targetSum, memory);
    }

    /**
     * 定义一个函数: 通过nums中的所有数字, 获取可以 凑成剩余差为remainDifference所有解法
     *
     * @param nums             数组
     * @param index            数组起始索引
     * @param remainDifference 剩余差
     * @return 可以凑成 剩余差为remainDifference所有解法.
     */
    private int getCountOfRemainDiffIsZeroByNums(int[] nums, int index, int remainDifference,
                                                 Map<String, Integer> memory) {
        // base case
        if (index == nums.length) {
            if (remainDifference == 0) {
                return 1;
            }
            return 0;
        }
        String key = index + "," + remainDifference;
        if (memory.containsKey(key)) {
            return memory.get(key);
        }

        // 第i个位置选择 +nums[index], 凑成 剩余差 为remainDifference - nums[index] 有 countOne种解法
        int countOne = getCountOfRemainDiffIsZeroByNums(nums, index + 1,
                remainDifference - nums[index], memory);
        // 第i个位置选择 -nums[index], 凑成 剩余差 为remainDifference +nums[index] 有 countTwo种解法
        int countTwo = getCountOfRemainDiffIsZeroByNums(nums, index + 1,
                remainDifference + nums[index], memory);
        int count = countOne + countTwo;
        // 两种解法合并起来. 即用了0~index个数字,并且凑够剩余差为 remainDifference 有count种情况.
        memory.put(key, count);
        return count;
    }
}
