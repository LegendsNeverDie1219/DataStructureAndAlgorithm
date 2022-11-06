package com.atguigu.leetcode.ChapterOne.linkedlistdoublepointer;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/6 20:44
 */
@Setter
@Getter
public class ListNode {
    public int val;

    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                '}';
    }
}
