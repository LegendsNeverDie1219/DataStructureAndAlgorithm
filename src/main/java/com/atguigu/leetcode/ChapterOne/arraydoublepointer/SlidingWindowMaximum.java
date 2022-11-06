package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/27 15:27
 */
public class SlidingWindowMaximum {
    /**
     * 给你⼀个整数数组 nums，有⼀个⼤⼩为 k 的滑动窗⼝从数组的最左侧移动到数组的最右侧，返回滑动窗⼝
     * 中的最⼤值。
     * 滑动窗⼝每次只向右移动⼀位，你只可以看到在滑动窗⼝内的 k 个数字。
     * 示例 1：
     * 输⼊：nums = [1,3,-1,-3,5,3,6,7], k = 3
     * 输出：[3,3,5,5,6,7]
     * 解释：
     * 滑动窗⼝的位置 最⼤值
     * --------------- -----
     * [1 3 -1] -3 5 3 6 7 3
     * 1 [3 -1 -3] 5 3 6 7 3
     * 1 3 [-1 -3 5] 3 6 7 5
     * 1 3 -1 [-3 5 3] 6 7 5
     * 1 3 -1 -3 [5 3 6] 7 6
     * 1 3 -1 -3 5 [3 6 7] 7
     */
    @Test
    public void testSlidingWindowMaximum() {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] maxValueArr = maxSlidingWindow(nums, k);
        System.out.println(Arrays.toString(maxValueArr));
    }

    /**
     * 思路:
     * 1.首先是使用一个队列来刻画这个滑动窗口,
     * 在前[0,k-2]次的时候,需要往队列中push数据push(arr[i]),
     * 从第k次开始此后每次都是push(arr[i])一个元素, 然后计算窗口中数组的最大值,接着pop(arr[i-k+1])窗口的第一个元素
     * 2.这个队列要保证是一个单调递减的队列,即每次push新的元素i的时候,要把队列中比i小的元素全部删除掉.
     *
     * @param nums 整数数组 nums
     * @param k    ⼤⼩为 k 的滑动窗⼝
     * @return 返回滑动窗⼝的最大值对应的数组
     */
    private int[] maxSlidingWindow(int[] nums, int k) {
        MonotonicQueue monotonicQueue = new MonotonicQueue();
        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // [0,k-2]
            if (i < k - 1) {
                monotonicQueue.offerData2Tail(nums[i]);
            } else {
                // [k-1,nums.length-1]
                monotonicQueue.offerData2Tail(nums[i]);
                Integer maxValue = monotonicQueue.getMaxValue();
                resultList.add(maxValue);

                monotonicQueue.pollData2Head(nums[i + 1 - k]);
            }
        }

        int[] resultArr = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            resultArr[i] = resultList.get(i);
        }
        return resultArr;
    }

    private static class MonotonicQueue {
        private final LinkedList<Integer> linkedList = new LinkedList<>();

        public void offerData2Tail(int newNumber) {
            while (!linkedList.isEmpty() && linkedList.getLast() < newNumber) {
                // 移除比number小的尾部元素
                linkedList.pollLast();
            }
            linkedList.addLast(newNumber);
        }

        public void pollData2Head(int headNumber) {
            // 移除头部元素,前提是头部元素存在
            if (headNumber == linkedList.getFirst()) {
                linkedList.pollFirst();
            }
        }

        public Integer getMaxValue() {
            return linkedList.getFirst();
        }

    }
}
