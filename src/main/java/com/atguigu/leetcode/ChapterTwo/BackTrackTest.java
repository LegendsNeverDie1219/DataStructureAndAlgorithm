package com.atguigu.leetcode.ChapterTwo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/22 8:23
 */
public class BackTrackTest {
    /**
     * 解决一个回溯算法,其实就是解决一个决策树的问题,
     * 1.明确路径链表 也就是已经做出的选择
     * 2.明确选择列表 也就是可以进行的选择
     * 3.明确结束条件 也就是到达决策树底层,无法再进行选择 的条件
     */
    //全排列问题
    @Test
    public void testTheWholeArrangement() {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> res = theWholeArrangement(nums);
        System.out.println(res);
    }

    private List<List<Integer>> theWholeArrangement(int[] nums) {
        // 记录所有路径的集合.
        List<List<Integer>> res = new ArrayList<>();
        // 记录某一条路径
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums,track,res);
        return res;
    }

    private void backtrack(int[] nums, LinkedList<Integer> track, List<List<Integer>> res) {
        // 如果到达结束条件(即决策树的底层,无法再选择.),则把其中一条路径加入到res中
        if(track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;
        }

        //
        for (int i = 0; i < nums.length;i++) {
            // 排除不合法的选择(没有显示的定义 选择列表, 而是通过 路径链表.contains(全部的选择集合中的某一个) 来确定合法的选择.)
            if(track.contains(i)) {
                continue;
            }
            //做选择.
            track.add(i);

            // 递归执行
            // 进入下一层决策树
            backtrack(nums,track,res);
            // 撤销选择
            track.removeLast();
        }
    }
}
