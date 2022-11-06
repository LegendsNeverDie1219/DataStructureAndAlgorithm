package com.atguigu.leetcode.ChapterOne.arraydoublepointer.other;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/30 8:16
 */
public class RemoveElement {
    /**
     * 给你⼀个数组 nums 和⼀个值 val，你需要原地移除所有数值等于 val 的元素，并返回移除后数组的新⻓
     * 度。
     * 你必须仅使⽤ O(1) 额外空间并原地修改输⼊数组。元素的顺序可以改变，你不需要考虑数组中超出新⻓度
     * 后⾯的元素。
     */
    public static void main(String[] args) {
        int[] nums = new int[] {0,0,1,2,2,3,3};
        int val = 1;
        int length = removeElement(nums,val);
        System.out.println(length);
        Arrays.fill(nums,length,nums.length,0);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 思路:使用双指针front,back, back走在后面,front走在前面,当front发现一个元素为nums[front] == val 则跳过,即只有front移动
     * 否则,nums[front]赋值给nums[back],并且front,back一起前移一步.
     * @param nums nums
     * @param val val
     * @return 移除后数组的新长度
     */
    private static int removeElement(int[] nums, int val) {
        int front =0;
        int back = 0;
        while(front < nums.length) {
            // if (nums[front] == val) {
            //     front++;
            // } else {
            //     nums[back] = nums[front];
            //     front++;
            //     back++;
            // }
            if(nums[front] != val) {
                nums[back] = nums[front];
                back++;
            }
            front++;
        }
        return back;

    }
}
