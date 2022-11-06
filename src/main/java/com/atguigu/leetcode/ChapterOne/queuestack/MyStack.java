package com.atguigu.leetcode.ChapterOne.queuestack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用队列实现栈
 *
 * @author Administrator
 * @date 2022/4/20 7:23
 */
public class MyStack {
    /**
     * 用队列实现栈
     */
    Queue<Integer> integerQueue = new LinkedList<>();

    int topOfStackElementsPoint;

    /**
     * 数据入栈
     *
     * @param element element
     */
    public void push(Integer element) {
        integerQueue.offer(element);
        topOfStackElementsPoint = element;
    }

    /**
     * 数据出栈
     *
     * @return 返回栈顶的元素.
     */
    public Integer pop() {
        // 思路: 需要先将队列中最后的一个元素 前面的所有元素 都添加到队列的尾部.
        // 然后,原来队列的最后一个元素 就变成了新队列的第一个元素, 该元素出队列.
        // 并且原来队列的倒数第2个元素 就变成了新队列的第二个元素, 就成了栈顶元素.
        int size = integerQueue.size();
        while (size > 2) {
            integerQueue.offer(integerQueue.poll());
            size--;
        }
        if (!integerQueue.isEmpty()) {
            // 倒数第二个元素出队列,并设置为栈顶元素, 并添加到队列尾部.
            topOfStackElementsPoint = integerQueue.poll();
            integerQueue.offer(topOfStackElementsPoint);
        }
        // 倒数第一个元素出队列.
        return integerQueue.poll();
    }

    /**
     * 查看栈顶元素
     *
     * @return 栈顶元素
     */
    public Integer peek() {
        return topOfStackElementsPoint;
    }

    /**
     * 判断栈是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return integerQueue.isEmpty();
    }

}
