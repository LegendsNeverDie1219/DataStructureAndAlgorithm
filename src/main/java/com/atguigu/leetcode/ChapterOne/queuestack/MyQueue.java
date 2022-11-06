package com.atguigu.leetcode.ChapterOne.queuestack;

import java.util.Stack;

/**
 * 用双栈实现队列
 *
 * @author Administrator
 * @date 2022/4/20 7:58
 */
public class MyQueue {
    private final Stack<Integer> stack1 = new Stack<>();
    private final Stack<Integer> stack2 = new Stack<>();

    /**
     * 元素入队列
     *
     * @param element element
     */
    public void offer(int element) {
        stack1.push(element);
    }

    /**
     * 元素出队列
     *
     * @return 队列的头部元素.
     */
    public Integer poll() {
        peek();
        return stack2.pop();
    }

    /**
     * 查询队列的头部元素
     *
     * @return 队列的头部元素
     */
    public Integer peek() {
        // 如果栈2为空,则循环将栈1中的元素都出栈然后添加到栈2中
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.peek();
    }

    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }
}
