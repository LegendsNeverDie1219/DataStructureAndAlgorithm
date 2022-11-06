package com.atguigu.leetcode.ChapterThree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/26 20:21
 */
public class BackTrack1 {
    /**
     * 求解子集问题:
     * 输入一个不包含重复数字的数组，要求算法输出这些数字的所有子集
     */
    @Test
    public void testGetSubSets() {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> subSets = getSubSets(nums);
        subSets.forEach(list -> {
            String str = list.stream().map(String::valueOf).collect(Collectors.joining(" "));
            System.out.println(str);
        });
    }

    public static List<List<Integer>> getSubSets(int[] nums) {
        // 求解子集问题
        List<List<Integer>> subSets = new ArrayList<>();
        // 定义一个回溯函数,专门用来遍历nums.并收集合法的子集 到subSets中.
        // 第一次调用的时候从数组中的第一个元素开始.
        LinkedList<Integer> trackList = new LinkedList<>();
        backTrackForSubSets(nums, 0, trackList, subSets);

        return subSets;
    }

    private static void backTrackForSubSets(int[] nums, int start, LinkedList<Integer> trackList,
                                            List<List<Integer>> subSets) {
        // base case
        subSets.add(new LinkedList<>(trackList));

        // 第一次进入时的选择列表[0,nums.length-1]
        // 第2次进入时的选择列表[1,nums.length-1]
        for (int i = start; i < nums.length; i++) {
            // 做选择
            trackList.addLast(nums[i]);
            // 进入下一层决策树
            backTrackForSubSets(nums, i + 1, trackList, subSets);
            // 取消选择
            trackList.removeLast();
        }
    }

    @Test
    public void testCombine() {
        // 交换顺序之后,不会产生新的结果,所以要剪枝
        List<List<Integer>> combine = combine(4, 2);

        combine.forEach(list -> {
            String str = list.stream().map(String::valueOf).collect(Collectors.joining(" "));
            System.out.println(str);
        });
    }

    /**
     * 输入两个数字 n, k，算法输出 [1..n] 中 k 个数字的所有组合。
     */
    public List<List<Integer>> combine(int n, int k) {
        if (k > n) {
            return new ArrayList<>();
        }
        List<List<Integer>> resultList = new ArrayList<>();
        LinkedList<Integer> trackList = new LinkedList<>();
        backTrackForCombine(n, 1, trackList, resultList, k);
        return resultList;
    }

    private void backTrackForCombine(int n, int start, LinkedList<Integer> trackList, List<List<Integer>> resultList,
                                     int k) {
        //base case  k决定了决策树的高度 , n决定了决策树的宽度.
        if (trackList.size() == k) {
            resultList.add(new LinkedList<>(trackList));
            return;
        }

        for (int i = start; i <= n; i++) {
            trackList.addLast(i);
            backTrackForCombine(n, i + 1, trackList, resultList, k);
            trackList.removeLast();
        }
    }

    @Test
    public void testPermute() {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> combine = permute(nums);

        combine.forEach(list -> {
            String str = list.stream().map(String::valueOf).collect(Collectors.joining(" "));
            System.out.println(str);
        });

    }

    private List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        LinkedList<Integer> trackLinkedList = new LinkedList<>();
        backTrackByPermute(nums, trackLinkedList, resultList);
        return resultList;
    }

    private void backTrackByPermute(int[] nums, LinkedList<Integer> trackLinkedList, List<List<Integer>> resultList) {
        //base case
        if (trackLinkedList.size() == nums.length) {
            resultList.add(new LinkedList<>(trackLinkedList));
            return;
        }
        for (int num : nums) {
            if (trackLinkedList.contains(num)) {
                continue;
            }
            trackLinkedList.addLast(num);
            backTrackByPermute(nums, trackLinkedList, resultList);
            trackLinkedList.removeLast();
        }
    }

    @Test
    public void testSumOfCombine() {
        int[] candidates = new int[]{2, 3, 5};
        int targetSum = 8;
        List<List<Integer>> resultList = sumOfCombine(candidates, targetSum);
        resultList.forEach(list -> {
            String str = list.stream().map(String::valueOf).collect(Collectors.joining(",", "{", "}"));
            System.out.println(str);
        });
    }

    /**
     * 给定⼀个⽆重复元素的正整数数组 candidates 和⼀个正整数 target ，找出 candidates 中所有可以使
     * 数字和为⽬标数 target 的唯⼀组合。
     * 提示：candidates 中的数字可以⽆限制重复被选取。如果⾄少⼀个所选数字数量不同，则两种组合是唯⼀
     * 的。
     * 示例 1：
     * 输⼊：candidates = [2,3,6,7], target = 7
     * 输出：[[7],[2,2,3]]
     * 示例 2：
     * 输⼊：candidates = [2,3,5], target = 8
     * 输出：[[2,2,2,2],[2,3,3],[3,5]]
     */
    public List<List<Integer>> sumOfCombine(int[] candidates, int targetSum) {
        List<List<Integer>> resList = new ArrayList<>();
        if(candidates.length == 0) {
            return resList;
        }
        // 选择: 第start个位置要要放置那个数字(start~length-1)(由于是组合问题:所以交换元素的顺序,不会产生新的节点, 所以回溯树从左到右一直在缩小.)
        int start = 0;
        // 路径: 前面 start-1 个位置已经做出的选择, 记录在LinkedList中 或者StringBuilder中.
        LinkedList<Integer> trackList = new LinkedList<>();
        // base case  触底条件, 正确结果, sum == targetSum 错误结果.  todo
        int sum = 0;

        backTrackForSumOfCombine(candidates, start, trackList, resList, sum, targetSum);
        return resList;
    }

    private void backTrackForSumOfCombine(int[] candidates, int start, LinkedList<Integer> trackList,
                                          List<List<Integer>> resList, int sum, int targetSum) {
        //触底条件
        if (start == candidates.length) {
            return;
        }
        // base 找到一个正确的结果.
        if (sum == targetSum) {
            resList.add(new LinkedList<>(trackList));
            return;
        }
        // base case 错误结果.
        if (sum > targetSum) {
            return;
        }

        for (int index = start; index < candidates.length; index++) {
            // 做选择
            sum += candidates[index];
            trackList.addLast(candidates[index]);
            // 进入下一层决策树 todo 这里下一层决策树 还可以使用candidates[start]这个元素.
            backTrackForSumOfCombine(candidates, start, trackList, resList, sum, targetSum);
            // 撤销选择
            sum -= candidates[index];
            trackList.removeLast();
        }
    }


}
