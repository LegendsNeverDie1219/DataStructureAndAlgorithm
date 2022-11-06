package com.atguigu.leetcode.ChapterTwo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/8 10:28
 */
public class GreedAlgorithm {
    /**
     * 获取区间集合中最多有多少个区间不重叠.
     *
     * @param intvs 区间集合. intvs=[[1,3],[2,4],[3,6]]
     * @return 不重叠的区间的最大值.
     */
    private int intervalSchedule(int[][] intvs) {
        /**
         * 思路:
         * 1.按照区间[start,end] 的end值对区间集合进行升序排序.
         * 2.每次都先取出一个结束时间最早的区间 x
         * 3.把与区间x重叠的众多区间 从区间集合中干掉.
         * 3.重复2,3 步骤 直到区间结合intvs 为空.
         */
        // 按照区间[start,end] 的end值对区间集合进行升序排序.
        Arrays.sort(intvs, new Comparator<int[]>() {
            @Override
            public int compare(int[] obj1, int[] obj2) {
                return Integer.compare(obj1[1], obj2[1]);
            }
        });

        int count = 1;
        int minIntervalEnd = intvs[0][1];
        for (int i = 1; i < intvs.length; i++) {
            int intervalStart = intvs[i][0];
            // 不重叠的情况
            if (minIntervalEnd <= intervalStart) {
                count++;
                minIntervalEnd = intvs[i][1];
            }
        }
        return count;
    }

    /**
     * 给定一个区间的集合intervals，其中 intervals[i] = [starti, endi]。
     * 返回 需要移除区间的最小数量，使剩余区间互不重叠。
     * <p>
     * 输入: intervals = [[1,2],[2,3],[3,4],[1,3]]
     * 输出: 1
     * 解释: 移除 [1,3] 后，剩下的区间没有重叠。
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        int maxNoRepeatInterval = intervalSchedule(intervals);
        return intervals.length - maxNoRepeatInterval;
    }

    @Test
    public void test() {
        // [[1,2],[2,3],[3,4],[1,3]]
        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        int i = eraseOverlapIntervals(intervals);
        System.out.println(i);
    }

    /**
     * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组points，其中points[i] = [xstart, 
     * xend]表示水平直径在xstart和xend之间的气球。你不知道气球的确切 y 坐标。
     *
     * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足 xstart≤ x ≤ xend，则该气球会被 
     * 引爆。可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。
     *
     * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数。
     */
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] obj1, int[] obj2) {
                return Integer.valueOf(obj1[1]).compareTo(obj2[1]);
               // return Integer.compare(obj1[1], obj2[1]);
            }
        });

        int count = 1;
        int minIntervalEnd = points[0][1];
        for (int[] point : points) {
            int intervalStart = point[0];
            // 不重叠的情况
            if (minIntervalEnd < intervalStart) {
                count++;
                minIntervalEnd = point[1];
            }
        }
        return count;
    }
    // [[-2147483646,-2147483645],[2147483646,2147483647]]
    public int findMinArrowShots2(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] point1, int[] point2) {
                return Integer.compare(point1[1], point2[1]);
            }
        });
        int pos = points[0][1];
        int ans = 1;
        for (int[] balloon: points) {
            if (balloon[0] > pos) {
                pos = balloon[1];
                ++ans;
            }
        }
        return ans;
    }

    @Test
    public void findMinArrowShots2() {
        int[][] intervals = {{-2147483646,-2147483645},{2147483646,2147483647}};
        int i = findMinArrowShots(intervals);
        System.out.println(i);
    }
    @Test
    public void test1() {
        Integer integer1 = 2147483647;
        Integer integer2 = -2147483645;
        int reuslt = integer1 - integer2;
        System.out.println(reuslt);


    }
}
