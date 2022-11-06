package com.atguigu.oj;

import java.util.Arrays;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/15 16:46
 */
public class OptimalUpgradeTimeWindow {
    /**
     * 现在给定长度为: 168(7*24)的整数数组, 表示一个周期
     * <p>
     * 最佳升级时间窗选择规则如下:
     * <p>
     * 时间窗内累计用户访问量必须小于等于给定的容忍值.
     * 时间窗必须是连续的x个小时.最大的x即为 最佳升级时间窗(区间) 且不超过7*24小时
     * 时间窗允许跨区间,例如当前周期的第167小时,  到下一周期的第166小时. 是一个长度为168的时间窗
     * 请计算最佳升级时间窗, 并返回其开始时间和结束时间的数组下标.如果存在多个最佳的升级时间窗,则返回开始时间下标最小的一个.
     * <p>
     * 输入:
     * 第一行为整数n,表示给定的升级影响的容忍值. 取值范围[0,2^31]
     * 第二行为7*24个整数,表示一个周期(7*24)的每个小时的用户访问量.
     * <p>
     * 6
     * 1 2 3.....
     * <p>
     * 输出:
     * 两个整数, 分别表示所计算出的最佳升级时间窗的开始时间下标. 和结束时间下标.,不存在返回 -1,-1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pvErrorTolerance = Integer.parseInt(scanner.nextLine());

        int[] pvByHourWeekly = new int[7 * 24];
        for (int i = 0; i < pvByHourWeekly.length; i++) {
            pvByHourWeekly[i] = scanner.nextInt();
        }
        scanner.close();
        int[] results = getBestTimeWindow(pvByHourWeekly, pvErrorTolerance);
        String[] strResults = Arrays.stream(results).mapToObj(String::valueOf).toArray(String[]::new);
        System.out.println(String.join(" ", strResults));

    }

    private static int[] getBestTimeWindow(int[] pvByHourWeekly, int pvErrorTolerance) {
        // 思路, 采用滑动窗口的解题思想(left,right双指针初始化时都指向区间/字符串的开始, 窗口的范围是 [0,168*2))
        int sourceLength = pvByHourWeekly.length;
        int[] intervalByHourWeekly = new int[sourceLength * 2];
        for (int i = 0; i < intervalByHourWeekly.length; i++) {
            intervalByHourWeekly[i] = pvByHourWeekly[i % sourceLength];
        }

        // 窗口[left,right)
        int left = 0;
        int right = 0;
        long sumServerNum = 0;
        int[] res = new int[]{-1, -1};
        while (right < intervalByHourWeekly.length) {
            int moveInHourOfServerNum = intervalByHourWeekly[right];

            sumServerNum += moveInHourOfServerNum;
            // 窗口对应的小时区间 不再符合容忍度的要求
            while (sumServerNum > pvErrorTolerance) {
                int moveOutHourOfServerNum = intervalByHourWeekly[left];
                sumServerNum -= moveOutHourOfServerNum;
                left++;
            }
            // 符合容忍度要求,并且比上次的区间宽度 宽.
            if (right - left >= res[1] - res[0]) {
                // todo
                res[1] = right % sourceLength;
                res[0] = left % sourceLength;
            }
            // todo
            right++;
        }
        return res;
    }

}
