package com.atguigu.leetcode.ChapterTwo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/21 20:50
 */
public class DynamicProgressTest {
    @Test
    public void testCoinMakeChanges() {
        // int[] coins = new int[]{1, 2, 5};
        int[] coins = new int[]{2};
        // int amount = 11;
        int amount = 3;
        int result = coinMakeChanges(coins, amount);
        System.out.println(result);
    }

    /**
     * 硬币凑零钱
     *
     * @param coins  coins
     * @param amount amount
     * @return 最少的硬币数量
     */
    // 求最值, 并且是存在重叠子问题,最优子结构,是动态规划问题
    // 明确状态 就是会变化的东西, 即目标金额
    // 明确选择  就是引起状态变化的动作, 即选择不同面值的硬币
    // 明确dp(n)数组  即要凑齐 n元的金额,至少需要dp(n)枚硬币.
    // 明确base case
    private int coinMakeChanges(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }
        int result = Integer.MAX_VALUE;
        for (int coin : coins) {
            // 要想知道凑齐amount元的金额,至少需要多少枚硬币.
            // 就需要先计算出凑齐amount-coins[0] ,amount-coins[1],amount-coins[2]需要多少枚硬币. 然后+1
            // 最后求最小值.
            int subProblem = coinMakeChanges(coins, amount - coin);
            // 子问题的结果是-1 ,表明凑不出.直接跳过.
            if (subProblem == -1) {
                continue;
            }
            result = Math.min(result, subProblem + 1);
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    @Test
    public void testCoinMakeChangesByMemory() {
        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        int result = coinMakeChanges2(coins, amount);
        System.out.println(result);
    }

    public int coinMakeChanges2(int[] coins, int amount) {
        int[] memory = new int[amount + 1];
        Arrays.fill(memory, -666);
        return coinMakeChangesByMemory(coins, amount, memory);
    }

    private int coinMakeChangesByMemory(int[] coins, int amount, int[] memory) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }
        //每次执行之前需要先从备忘录中取出,看是否已经计算过.
        if (memory[amount] != -666) {
            // 说明 要凑够amount 金额,至少需要多少枚,硬币的问题 已经计算过.
            return memory[amount];
        }
        int result = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subProblem = coinMakeChangesByMemory(coins, amount - coin, memory);
            // 子问题无解. 跳过.
            if (subProblem == -1) {
                continue;
            }
            // 子问题有解
            result = Math.min(result, subProblem + 1);
        }
        // 将解的情况存入备忘录中.
        memory[amount] = result == Integer.MAX_VALUE ? -1 : result;
        return memory[amount];
    }

    @Test
    public void testDpArray() {
        //
        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        int result = coinMakeChangesByDp(coins, amount);
        System.out.println(result);
    }

    // 求最值, 并且是存在重叠子问题,最优子结构,是动态规划问题
    // 明确状态 就是会变化的东西, 即目标金额
    // 明确选择  就是引起状态变化的动作, 即选择不同面值的硬币
    // 明确dp(n)数组  即要凑齐 n元的金额,至少需要dp(n)枚硬币.
    // 明确base case
    private int coinMakeChangesByDp(int[] coins, int amount) {
        int[] dpArray = new int[amount + 1];
        // 填充的值要大于amount, 即凑齐amount元, 最多使用的硬币数量是 amount,即 全是1元. 即不可能取到的值
        Arrays.fill(dpArray, amount + 666);
        // base case
        dpArray[0] = 0;
        // 遍历所有可能的状态.
        for (int i = 0; i < amount + 1; i++) {
            // 在某一个状态的前提下.即在某一个金额i的前提下, 就计算凑齐金额i 需要的最少硬币数量dp[i]
            // 在求所有子问题 + 1 的最小值
            for (int coin : coins) {
                if (i - coin < 0) {
                    continue;
                }
                dpArray[i] = Math.min(dpArray[i], dpArray[i - coin] + 1);
            }
        }

        return dpArray[amount] == amount + 666 ? -1 : dpArray[amount];
    }

    @Test
    public void testBackPackProblem() {
        int[] weightArr = new int[]{1, 4, 3};
        int[] valueArr = new int[]{1500, 3000, 2000};
        //String[] goodsArr = new String[]{"空", "吉它", "音响", "电脑"};
        int backPackCapacity = 4;
        int maxValue = backPackProblem(weightArr, valueArr, backPackCapacity);
        System.out.println(maxValue);
    }

    /**
     * 背包问题
     *
     * @param weightArr        weightArr
     * @param valueArr         valueArr
     * @param backPackCapacity backPackCapacity
     * @return 可供选择的物品有三个, 把前3个物品装入背包的最大价值.
     * <p>
     * 思路:
     * 1.明确状态: 物品的重量, 物品的价值,
     * 2.明确选择; 把该物品装入背包, 把该物品不装入背包
     * 3.明确dp[i][j] 数组,即 从前i个物品中做选择,装入容量为j的背包的最大价值.
     * 4.明确base case
     * 即 物品选择为0时,不管容量多大, dp[0][j] = 0
     * 即 背包容量为0时,不管物品选择多少, dp[i][0] = 0
     */
    private int backPackProblem(int[] weightArr, int[] valueArr, int backPackCapacity) {
        int number = weightArr.length;
        int[][] dpArray = new int[number + 1][backPackCapacity + 1];
        // base case
        Arrays.fill(dpArray[0], 0);
        for (int i = 0; i < dpArray.length; i++) {
            dpArray[i][0] = 0;
        }
        // 遍历 两种状态的所有值.
        // 即在 前i个物品 ,以及容量为j 两种状态的前提下,进行选择,得到最大价值.
        for (int i = 1; i < dpArray.length; i++) {
            for (int j = 1; j < dpArray[i].length; j++) {
                // 第i个物品放不下. 只能沿用上一次选择.
                if (weightArr[i - 1] > j) {
                    // 从前i个物品中做选择,装入容量为j的背包的最大价值.
                    dpArray[i][j] = dpArray[i - 1][j];
                } else {
                    dpArray[i][j] = Math.max(dpArray[i - 1][j],
                            dpArray[i - 1][j - weightArr[i - 1]] + valueArr[i - 1]);
                }
            }
        }
        return dpArray[number][backPackCapacity];
    }

}
