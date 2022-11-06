package com.atguigu.leetcode.ChapterOne.linkedlistdoublepointer;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/6 20:42
 */
public class LinkedListDoublePointerTest2 {
    // 后驱节点
    private ListNode afterNode = null;

    /**
     * 给定⼀个头结点为 head 的⾮空单链表，返回链表的中间结点；如果有两个中间结点，则返回第⼆个中间结
     * 点。
     * 示例 1：
     * 输⼊：[1,2,3,4,5]
     * 输出：此列表中的结点 3（序列化形式：[3,4,5])
     * 返回的结点值为 3。（测评系统对该结点序列化表述是 [3,4,5])。
     * 注意，我们返回了⼀个 ListNode 类型的对象 ans，这样：
     * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及
     * ans.next.next.next = NULL.
     * 示例 2：
     * 输⼊：[1,2,3,4,5,6]
     * 输出：此列表中的结点 4（序列化形式：[4,5,6])
     * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第⼆个结点。
     */
    public ListNode getMiddleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    @Test
    public void testGetMiddleNode() {
        ListNode headOne = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        System.out.println(getMiddleNode(headOne));

        ListNode headTwo = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5));
        System.out.println(getMiddleNode(headTwo));
    }

    /**
     * 给你⼀个链表，请你对每 k 个节点⼀组进⾏翻转，返回翻转后的链表。
     * k 是⼀个正整数，它的值⼩于或等于链表的⻓度，如果节点总数不是 k 的整数倍，那么请将最后剩余的节点
     * 保持原有顺序。
     * 你不能只是单纯的改变节点内部的值，⽽是需要实际进⾏节点交换。
     * 输⼊：head = [1,2,3,4,5], k = 2
     * 输出：[2,1,4,3,5]
     */


    /**
     * 简化: 给定链表头结点，如何反转整个链表
     * [反转以 a 为头结点的链表」其实就是「反转 a 到 null 之间的结点]
     * [a,null)
     * 迭代的方式
     */
    public ListNode reverseTheWholeLinkedList(ListNode head) {
        // 1.首先初始化2个指针,pre,cur,nxt分别指向 链表的第一个元素的前面,第一个元素,第一个元素
        // 2.然后让cur.next = pre
        // 3.接着pre前移一步,cur前移一步. 循环执行,退出条件为 cur == null;
        // 最后,pre指向的就是反转后的链表的头节点.
        ListNode pre = null;
        ListNode cur = head;
        ListNode nxt = head;
        while (cur != null) {
            nxt = nxt.next;
            cur.next = pre;
            // pre前移一步,cur前移一步
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    @Test
    public void testReverseTheWholeLinkedList() {
        ListNode headOne = ListNodeUtils.generateListNodes2(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        ListNode listNode = reverseTheWholeLinkedList(headOne);
        ListNodeUtils.showListNodes(listNode);
    }

    /**
     * 反转区间内的节点集合.[a, b) ，注意是左闭右开 ,中间临时方案:
     * 其中nodeA前面的节点 preNode的next域 还未指向 -> pre.
     * nodeA节点的next域 还未指向nodeB.nodeA.next = nodeB
     */
    public ListNode reverseNodesInTheInterval(ListNode nodeA, ListNode nodeB) {
        ListNode pre = null;
        ListNode cur = nodeA;
        ListNode nxt = nodeA;
        while (cur != nodeB) {
            nxt = nxt.next;
            cur.next = pre;

            pre = cur;
            cur = nxt;
        }
        return pre;
    }

    /**
     * 现在我们迭代实现了反转部分链表的功能，接下来就按照之前的逻辑编写 reverseKGroup 函数即可：
     * 1 2 3 4 5 6
     */
    /**
     * 思路: 先反转以head开头的k个元素,
     * 然后,将第k+1个元素作为head元素,进行反转 即递归执行reverseKGroup
     * 最后,将两个过程的结果连接起来.
     */
    public ListNode reverseKGroup(ListNode head, Integer k) {
        // 基础案例 base base
        // 如果剩余节点总数 <k，那么最后剩余的节点保持原有顺序
        if (head == null) {
            return null;
        }

        // 区间[nodeA,nodeB) 需要包含k个待反转的节点. 需要移动移动k步, [head,head+k)
        ListNode nodeA = head;
        ListNode nodeB = head;
        for (int i = 0; i < k; i++) {
            if (nodeB == null) {
                return head;
            }
            nodeB = nodeB.next;
        }

        // [head,nodeB)
        ListNode newHead = reverseNodesInTheInterval(nodeA, nodeB);
        // ListNode listNode = reverseKGroup(nodeB, k);
        //
        // // head的next域 指向 nodeB.
        // head.next = nodeB;
        nodeA.next = reverseKGroup(nodeB, k);
        return newHead;
    }

    @Test
    public void testReverseKGroup() {
        ListNode headOne = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        ListNode listNode = reverseKGroup(headOne, 2);
        ListNodeUtils.showListNodes(listNode);
    }

    public ListNode deleteDuplicateElements(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;
        // 让快指针在前面探探路,如果发现一个不重复的元素,就告诉满指针,让慢指针前进一步
        while (fast != null) {
            if (fast.val != slow.val) {
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        // 断开与后面重复元素的连接
        slow.next = null;
        return head;
    }

    @Test
    public void testRemovesDuplicateElementsFromTheLinkedList() {
        ListNode headOne = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 1, 3, 4, 2, 2));
        ListNode listNode = deleteDuplicateElements(headOne);
        System.out.println(listNode);
    }

    /**
     * 递归的方式: 将以head为头结点的链表进行反转,返回反转之后的头结点.
     *
     * @param head 初始链表的头结点
     * @return 反转之后链表的头结点
     */
    public ListNode reverseTheWholeLinkedList2(ListNode head) {
        // base case 链表中只有一个节点, 则该节点无需反转,直接返回
        if (head.next == null) {
            return head;
        }
        // 从第二个节点开始递归,进行反转操作.
        ListNode newHead = reverseTheWholeLinkedList2(head.next);
        // 最后一次调用时: 即第二个节点的next指针 指向初始的head节点
        head.next.next = head;
        // 初始的head节点的next指针 指向null;
        head.next = null;
        return newHead;
    }

    @Test
    public void testReverseTheWholeLinkedList2() {
        ListNode head = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        ListNode newHead = reverseTheWholeLinkedList2(head);
        ListNodeUtils.showListNodes(newHead);
    }

    /**
     * 将链表的前N个节点进行反转.
     *
     * @param head 头结点
     * @param n    前n个节点 n< length
     * @return 反转之后链表的头结点 newHead
     */
    public ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            // 记录第n+1个节点.
            afterNode = head.next;
            return head;
        }
        ListNode newHead = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = afterNode;
        return newHead;
    }

    @Test
    public void testReverseNMethod() {
        ListNode head = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        afterNode = null;
        ListNode newHead = reverseN(head, 2);
        ListNodeUtils.showListNodes(newHead);
    }

    /**
     * 给你单链表的头指针 head 和两个整数 left 和 right，其中 left <= right，请你反转从位置 left 到
     * 位置 right 的链表节点，返回反转后的链表。
     */
    public ListNode reverseNodesInTheInterval2(ListNode head, int m, int n) {
        // base case
        if (m == 1) {
            return reverseN(head, n);
        }
        // 如果把初始head节点视为索引1, 则是反转第m~n个节点
        // 如果把head.next节点视为索引1, 则是反转第m-1~n-1个节点.
        head.next = reverseNodesInTheInterval2(head.next, m - 1, n - 1);
        return head;
    }

    @Test
    public void testreverseNodesInTheInterval2() {
        ListNode head = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        afterNode = null;
        ListNode newHead = reverseNodesInTheInterval2(head, 3, 5);
        ListNodeUtils.showListNodes(newHead);
    }

    @Test
    public void testIsPalindrome() {
        ListNode head = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 2, 1));
        //ListNode head = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 2, 1));
        boolean palindrome = isPalindrome(head);
        System.out.println(palindrome);
        ListNodeUtils.showListNodes(head);
    }

    /**
     * 给你⼀个单链表的头节点 head，请你判断该链表是否为回⽂链表。如果是，返回 true；否则返回 false。
     * 输⼊：head = [1,2,2,1]
     * 输出：true
     *  思路: 由于单链表无法倒着遍历, 所以最简单的做法是,将原链表复制一份copy,然后反转copy的链表. 最后进行比较
     *  比较巧妙的做法是:
     *  1. 先通过双指针找到链表的中点. 对于奇数个节点的链表来说. 此时fast指针指向的是最后一个节点. 对于偶数个,则fast指针指向的是null.
     *  2.如果是奇数个节点的链表, 则让slow再前进一步.
     *  3.反转[slow,null)的链表
     *  4.进行回文比较. head为首的链表1,以及以及最后的一个节点为首的链表2
     * @param head 头结点
     * @return 是否是回文链表.
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast != null) {
            // 则是链表的个数为奇数个,需要slow(中间节点) 前进一步
            slow = slow.next;
        }
        // 反转 slow- > end
        ListNode otherHeadNode = reverseSlow2End(slow);

        while(otherHeadNode != null) {
            if (head.val != otherHeadNode.val) {
                return false;
            }
            otherHeadNode = otherHeadNode.next;
            head = head.next;
        }
        return true;
    }

    private ListNode reverseSlow2End(ListNode slow) {
        ListNode pre = null;
        ListNode cur = slow;
        while(cur != null) {
            // 记录当前节点的后驱节点
            ListNode nxt = cur.next;

            // 反转链表
            cur.next = pre;

            // pre,cur右移
            pre = cur;
            cur = nxt;
        }
        return pre;
    }

}
