package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/25 8:24
 */
public class ZeroAfterFactorial {
    /**
     * 给定⼀个整数 n，返回 n! 结果中尾随零的数量。
     * 提示：n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
     * 示例 1：
     * 输⼊：n = 3
     * 输出：0
     * 解释：3! = 6，不含尾随 0
     */
    /**
     * 求n!阶乘 尾随零的个数,其实就是求数字n可以分解出多少个 因子 2 和因子 5,
     * 因子2 只要是个偶数就可以分解出来,会很多.所以本质上就是求数字n可以分解出多个个因为5
     * 例如: n= 125
     * 则125 包含的因子5的个数为: n/5 + n/(5*5) + n/(5*5*5) = 25 + 5 + 1 = 31
     * 即125! 尾随零的个数为 31个
     */
    @Test
    public void testZeroAfterFactorial() {
        int n = 125;
        long count = zeroAfterFactorial(n);
        System.out.println(count);
    }

    private long zeroAfterFactorial(long n) {
        long factorFiveCount = 0;
        int divisor = 5;
        while (divisor <= n) {
            factorFiveCount += n / divisor;
            divisor *= 5;
        }
        return factorFiveCount;
    }

    private long zeroAfterFactorial2(long n) {
        long factorFiveCount = 0;
        for (int divisor = 5; divisor <= n; divisor *= 5) {
            factorFiveCount += n / divisor;
        }
        return factorFiveCount;
    }
    /**
     * f(x) 是 x! 末尾是 0 的数量，x! = 1 * 2 * 3 * ... * x，且 0! = 1。
     * 例如，f(3) = 0，因为 3! = 6 的末尾没有 0；⽽ f(11) = 2，因为 11! = 39916800 末端有 2 个 0。给定
     * K，找出多少个⾮负整数 x，能满⾜ f(x) = K。
     * 示例 1：
     * 输⼊：K = 0
     * 输出：5
     * 解释：0!, 1!, 2!, 3!, and 4! 均符合 K = 0 的条件。
     */

    /**
     * 本质上就是问: 在[0,LONG_MAX] 范围内,可以找到多少个数字x 满足 f(x) = K(K是定值 2)
     * 本质上就是求 满足f(x) = K的右边界 rightBoundX 和左边界leftBoundX
     * int xNumberCount = rightBoundX - leftBoundX +1
     */
    @Test
    public void test() {
        int k = 0;
        //System.out.println(Long.MAX_VALUE);
        long numberXxCount = findMeetConditionNumberXxCount(k);
        System.out.println(numberXxCount);

    }

    private long findMeetConditionNumberXxCount(int k) {
        return rightBound2(k) - leftBound2(k) + 1;
    }

    private long leftBound(int k) {
        long left = 0;
        long right = Integer.MAX_VALUE;
        // [left,right)
        while (left < right) {
            long middleX = left + (right - left) / 2;
            long zeroCount = zeroAfterFactorial(middleX);
            if (zeroCount < k) {
                // [middleX+1, right) 搜索
                left = middleX + 1;
            } else if (zeroCount > k) {
                // [left,middleX) 搜索
                right = middleX;
            } else {
                // 向左收缩,锁定左边界
                right = middleX;
            }
        }
        return left;
    }

    private long leftBound2(int k) {
        long left = 0;
        long right = Integer.MAX_VALUE;
        // [left,right]
        while (left <= right) {
            long middleX = left + (right - left) / 2;
            long zeroCount = zeroAfterFactorial(middleX);
            if (zeroCount < k) {
                // [middleX+1, right] 搜索
                left = middleX + 1;
            } else if (zeroCount > k) {
                // [left,middleX-1] 搜索
                right = middleX - 1;
            } else {
                // 向左收缩,锁定左边界
                right = middleX - 1;
            }
        }
        return left;
    }


    private long rightBound(int k) {
        long left = 0;
        long right = Integer.MAX_VALUE;
        while (left < right) {
            long middleX = left + (right - left) / 2;
            long zeroCount = zeroAfterFactorial(middleX);
            if (zeroCount < k) {
                // [middleX+1, right) 搜索
                left = middleX + 1;
            } else if (zeroCount > k) {
                // [left,middleX) 搜索
                right = middleX;
            } else {
                // 向右收缩,锁定右边界
                left = middleX + 1;
            }
        }
        return right - 1;
    }

    private long rightBound2(int k) {
        long left = 0;
        long right = Integer.MAX_VALUE;

        while (left <= right) {
            long middleX = left + (right - left) / 2;
            long zeroCount = zeroAfterFactorial(middleX);
            if (zeroCount < k) {
                // [middleX+1, right] 搜索
                left = middleX + 1;
            } else if (zeroCount > k) {
                // [left,middleX-1] 搜索
                right = middleX - 1;
            } else {
                // 向右收缩,锁定右边界
                left = middleX + 1;
            }
        }
        return right;
    }
}
