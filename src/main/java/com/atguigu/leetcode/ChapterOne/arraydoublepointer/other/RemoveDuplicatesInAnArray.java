package com.atguigu.leetcode.ChapterOne.arraydoublepointer.other;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/30 7:55
 */
public class RemoveDuplicatesInAnArray {
    /**
     * 给你⼀个有序数组 nums，请你原地删除重复出现的元素，使每个元素只出现⼀次，返回删除后数组的新⻓
     * 度。
     * 不要使⽤额外的数组空间，你必须在原地修改输⼊数组并在使⽤ O(1) 额外空间的条件下完成。
     */
    @Test
    public void testRemoveDuplicates() {
        int[] nums = new int[] {0,0,1,2,2,3,3};
        int[] duplicateArr = removeDuplicates(nums);
        System.out.println(duplicateArr);
    }

    /**
     * 思路: 使用双指针,front,back,其中back走在后面,front走在前面,当front发现一个不重复的元素之后,
     * 就会告诉back指针,让back指针前进一步.并在back索引位置存储该元素
     * @param nums nums
     * @return int[]
     */
    private int[] removeDuplicates(int[] nums) {
        int front = 0;
        int back = 0;
        while (front < nums.length) {
            if (nums[front] != nums[back]) {
                back++;
                nums[back] = nums[front];
            }
            front++;
        }
        Arrays.fill(nums,back+1,nums.length,0);
        return nums;
    }
}
