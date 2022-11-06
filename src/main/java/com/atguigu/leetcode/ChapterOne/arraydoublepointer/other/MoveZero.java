package com.atguigu.leetcode.ChapterOne.arraydoublepointer.other;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/30 8:38
 */
public class MoveZero {
    public static void main(String[] args) {
        int[] nums = new int[] {100,100,1,2,100,3,3};
        int val = 100;
        int backIndex = removeElement(nums, val);
        Arrays.fill(nums,backIndex,nums.length,100);
        System.out.println(Arrays.toString(nums));

    }
    /**
     *  int[] nums = new int[] {100,100,1,2,100,3,3};
     * 即可以看成是先移除所有的100,然后把back后面的元素都填充为100
     */
    private static int removeElement(int[] nums, int val) {
        int front =0;
        int back = 0;
        while(front < nums.length) {
            if(nums[front] != val) {
                nums[back] = nums[front];
                back++;
            }
            front++;
        }
        return back;
    }
}
