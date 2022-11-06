package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/26 10:50
 */
public class SendPackageProblem {
    /**
     * 传送带上的第 i 个包裹的重量为 weights[i]，运输船每天都会来运输这些包裹，每天装载的包裹重量之和
     * 不能超过船的最⼤运载重量。如果要在 D 天内将所有包裹运输完毕，求运输船的最低运载能⼒。
     * 示例 1：
     * 输⼊：weights = [1,2,3,4,5,6,7,8,9,10], D = 5
     * 输出：15
     * 解释：
     * 船舶最低载重 15 就能够在 5 天内送达所有包裹，如下所示：
     * 第 1 天：1, 2, 3, 4, 5
     * 第 2 天：6, 7
     * 第 3 天：8
     * 第 4 天：9
     * 第 5 天：10
     * 请注意，货物必须按照给定的顺序装运，因此使⽤载重能⼒为 14 的船舶并将包装分成 (2, 3, 4,
     * 5), (1, 6, 7), (8), (9), (10) 是不允许的。
     */
    @Test
    public void testSendPackageProblem() {
        int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int D = 5;
        int minSpeed = sendPackageProblem(weights, D);
        System.out.println(minSpeed);
    }

    private int sendPackageProblem(int[] weights, int targetDays) {
        // 题目的本质就是求满足条件的最大时间, 即时间的右边界.即在[0,Integer.MAX_VALUE] 搜索D=5时的速度.
        // 由于速度和时间是成反比的,所以在搜搜索时间的右边界的时候, speed 指针的移动都取反.

        // 缩小搜索边界,以提高速度.
        int left = 0;
        int right = 0;
        for (int w : weights) {
            left = Math.max(left, w);
            right += w;
        }
        while (left < right) {
            int middleSpeed = left + (right-left)/ 2;
            int time = getTimeBySpeed(middleSpeed,weights);
            if (time < targetDays) {
                // 降低速度[left,middle-1]
                right = middleSpeed -1;
            } else if (time > targetDays) {
                // 提高速度 [middle+1,right]
                left = middleSpeed +1;
            } else {
                // 寻找time右边界,即speed的左边界, 向左移动,锁定左边界
                right = middleSpeed;
            }
        }
        return left;
    }
    // speed 即一天的运载量
    private int getTimeBySpeed(int speed, int[] weights) {
        int needDays = 0;
        for (int i = 0; i < weights.length;) {
            // 每开启新的一天 就重置 capacity
            int capacity = speed;
            while(i < weights.length && capacity -weights[i] >= 0) {
                capacity = capacity -weights[i];
                i++;
            }
            needDays++;
        }
        return needDays;
    }
}

