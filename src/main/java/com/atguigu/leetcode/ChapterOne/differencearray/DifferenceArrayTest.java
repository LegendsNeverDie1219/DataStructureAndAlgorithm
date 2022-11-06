package com.atguigu.leetcode.ChapterOne.differencearray;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/16 17:26
 */
public class DifferenceArrayTest {
    /**
     * 假设你有⼀个⻓度为 n 的数组，初始情况下所有的数字均为 0，你将会被给出 k 个更新的操作。
     * 其中，每个操作会被表示为⼀个三元组：[startIndex, endIndex, inc]，你需要将⼦数组
     * A[startIndex ... endIndex]（包括 startIndex 和 endIndex）增加 inc。
     * 请你返回 k 次操作后的数组。
     * 输⼊: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
     * 输出: [-2,0,3,5,3]
     */
    @Test
    public void test1() {
        int[] sourceArray = new int[5];
        DifferenceArray differenceArray = new DifferenceArray(sourceArray);
        differenceArray.increase(1, 3, 2);
        differenceArray.increase(2, 4, 3);
        differenceArray.increase(0, 2, -2);

        int[] resultArray = differenceArray.getResultArray();
        System.out.println(Arrays.toString(resultArray));
    }

    /**
     * 这⾥有 n 个航班，它们分别从 1 到 n 进⾏编号。有⼀份航班预订表 bookings，表中第 i 条预订记录
     * bookings[i] = [firsti, lasti, seatsi] 意味着在从 firsti 到 lasti（包含 firsti 和 lasti）
     * 的每个航班上预订了 seatsi 个座位。
     * 请你返回⼀个⻓度为 n 的数组 answer，⾥⾯的元素是每个航班预定的座位总数。
     * <p>
     * 输⼊：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
     * 输出：[10,55,45,25,25]
     * 解释：
     * 航班编号 1 2 3 4 5
     * 预订记录 1： 10 10
     * 预订记录 2： 20 20
     * 预订记录 3： 25 25 25 25
     * 总座位数： 10 55 45 25 25
     * 因此，answer = [10,55,45,25,25]
     */
    @Test
    public void testFlightReservationStatistics() {
        // 即flightArray[i-1,j-1]的座位增减情况.
        int[][] bookings = {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}};
        // 有多少个航班, 即原始数组flightArray的长度为5
        int n = 5;
        int[] resultArray = flightReservationStatistics(bookings, n);
        System.out.println(Arrays.toString(resultArray));
    }

    /**
     * 航班座位统计情况
     *
     * @param bookings 即flightArray[i-1,j-1]的座位增减情况.
     * @param n        有多少个航班, 即原始数组flightArray的长度为n
     * @return 结果数组
     */
    private int[] flightReservationStatistics(int[][] bookings, int n) {
        int[] flightSeatArray = new int[n];
        DifferenceArray differenceArray = new DifferenceArray(flightSeatArray);
        for (int index = 0; index < bookings.length; index++) {
            int startIndex = bookings[index][0] - 1;
            int endIndex = bookings[index][1] - 1;
            int value = bookings[index][2];
            differenceArray.increase(startIndex, endIndex, value);
        }
        return differenceArray.getResultArray();
    }

    /**
     * 你是⼀个开公交⻋的司机，公交⻋的最⼤载客量为 capacity，沿途要经过若⼲⻋站，给你⼀份乘客⾏程表
     * int[][] trips，其中 trips[i] = [num, start, end] 代表着有 num 个旅客要从站点 start 上⻋，
     * 到站点 end 下⻋，请你计算是否能够⼀次把所有旅客运送完毕（不能超过最⼤载客量 capacity）。
     * <p>
     * 输⼊：trips = [[2,1,5],[3,3,7]], capacity = 4
     * 输出：false
     */
    @Test
    public void testCarpooling() {
        int[][] trips = new int[][]{{2, 1, 5}, {3, 3, 7}};
        int capacity = 4;
        boolean result = carpooling(trips, capacity);
        System.out.println(result);
    }

    /**
     * 能否一次把所有旅客运送完毕.
     *
     * @param trips    旅客的行程表
     * @param capacity 公交车的最大容量
     * @return 能否一次把所有旅客运送完毕
     */
    private boolean carpooling(int[][] trips, int capacity) {
        // 1.原始数组busStationArray    即每一站的人员数量. 从第1站开始,最多有1000站.
        // 2.区间增减记录  即每一站的增减情况
        // 例如:[2,1,5], 即有2位乘客从1站上车, 从第5站下车. 即在busStationArray[1-1, 5-1-1]区间内增加了5名乘客.
        // 3.最终结果: 要求每一站车内人员数量 < capacity
        int[] busStationArray = new int[1000];
        DifferenceArray differenceArray = new DifferenceArray(busStationArray);
        for (int[] trip : trips) {
            int numberOfPassengers = trip[0];
            int startStation = trip[1];
            int endStation = trip[2];
            differenceArray.increase(startStation - 1, endStation - 2, numberOfPassengers);
        }
        int[] resultArray = differenceArray.getResultArray();
        System.out.println("resultArray:" + Arrays.toString(resultArray));
        for (int i = 0; i < resultArray.length; i++) {
            if (resultArray[i] > capacity) {
                return false;
            }
        }
        // offer,poll,peek,isEmpty()
        Queue<Integer> queue = new LinkedList<>();
        // push ,pop, peek ,empty
        Stack<Integer> stack = new Stack<>();
        return true;
    }

}
