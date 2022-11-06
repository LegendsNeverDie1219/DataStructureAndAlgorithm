package com.atguigu.leetcode.ChapterOne;/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/4 10:27
 */

import java.util.Collection;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/4 10:27
 */
public interface QueueHds<E> extends Collection<E> {
    @Override
    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}

