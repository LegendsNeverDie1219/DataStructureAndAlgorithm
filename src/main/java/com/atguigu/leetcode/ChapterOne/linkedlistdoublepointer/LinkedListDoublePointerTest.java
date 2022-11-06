package com.atguigu.leetcode.ChapterOne.linkedlistdoublepointer;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/4 15:05
 */
public class LinkedListDoublePointerTest {
    /**
     * 给你两个 ⾮空 的链表，表示两个⾮负的整数。它们每位数字都是按照 逆序 的⽅式存储的，并且每个节点只
     * 能存储 ⼀位 数字。
     * 请你将两个数相加，并以相同形式返回⼀个表示和的链表，你可以假设除了数字 0 之外，这两个数都不会以
     * 0 开头。
     * 输⼊：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     */
    @Test
    public void testAddTwoNumbers() {
        ListNode l1Head = ListNodeUtils.generateListNodes(Lists.newArrayList(2, 4, 5));
        ListNode l2Head = ListNodeUtils.generateListNodes(Lists.newArrayList(5, 6, 4));
        ListNode listNode = addTwoNumbers(l1Head, l2Head);
        ListNodeUtils.showListNodes(listNode);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 指针1 指向链表l1
        ListNode pointOne = l1;
        // 指针2 指向链表l2
        ListNode pointTwo = l2;
        // new 一个虚拟头节点,用来构建一条结果链表.
        ListNode virtualHead = new ListNode(-1);
        ListNode pointThree = virtualHead;
        // 进位
        int carry = 0;
        while (pointOne != null || pointTwo != null || carry != 0) { // todo
            // 先加上上一次的进位
            int value = carry;
            if (pointOne != null) {
                value = value + pointOne.val;
                pointOne = pointOne.next;
            }
            if (pointTwo != null) {
                value += pointTwo.val;
                pointTwo = pointTwo.next;
            }
            // 更新进位
            carry = value / 10;
            // 当前位置的value
            int currentPositionVal = value % 10;

            // 构建一个新的节点挂在虚拟头节点上
            pointThree.next = new ListNode(currentPositionVal);
            // 指针右移.
            pointThree = pointThree.next;
        }
        System.out.println("头结点: " + virtualHead);
        // 返回结果链表 ,去除无实际数据的头结点.
        return virtualHead.next;
    }

    @Test
    public void testDeleteTheKth2TheLastNode() {
        ListNode head = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5));
        //System.out.println(head);
        ListNode listNode = deleteTheKth2TheLastNode(head, 5);
        ListNodeUtils.showListNodes(listNode);
    }

    public ListNode deleteTheKth2TheLastNode(ListNode head, int kth2TheLast) {
        // 1.校验kth2TheLast的合法性.
        boolean checkResult = checkKth2TheLast(head, kth2TheLast);
        if (!checkResult) {
            return head;
        }
        // 2.首先找到链表中的倒数第K+1个节点 targetNode
        // 还是要构造一个虚拟的头节点,并且挂在当前链表的头部,目的是防止删除的是倒数第5个节点.(即正数第一个)
        ListNode virtualHead = new ListNode(-1);
        virtualHead.next = head;
        ListNode targetNode = findTheKth2TheLastNode(virtualHead, kth2TheLast + 1);
        // 3.然后删除掉  targetNode.next = targetNode.next.next
        targetNode.next = targetNode.next.next;
        return virtualHead.next;
    }

    private boolean checkKth2TheLast(ListNode head, int kth2TheLast) {
        ListNode point = head;
        int length = 0;
        while (point != null) {
            length++;
            point = point.next;
        }
        return kth2TheLast <= length;
    }

    private ListNode findTheKth2TheLastNode(ListNode virtualHead, int kth2TheLast) {
        // 使用快慢指针,先让快指针前进k步. 然后快慢指针再一起前进.直到快指针走到链表的尾部,此时慢指针指向的就是倒数第k个节点
        ListNode fast = virtualHead;
        ListNode slow = virtualHead;
        while (kth2TheLast > 0) {
            fast = fast.next;
            kth2TheLast--;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    @Test
    public void testMergesTwoOrderedLists() {
        ListNode headOne = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 4));
        ListNode headTwo = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 3, 4));

        ListNode listNode = mergesTwoOrderedLists(headOne, headTwo);
        ListNodeUtils.showListNodes(listNode);
    }

    /**
     * 输⼊两个升序链表，将它们合并为⼀个新的升序链表并返回。
     *
     * @param headOne headOne
     * @param headTwo headTwo
     * @return ListNode
     */
    public ListNode mergesTwoOrderedLists(ListNode headOne, ListNode headTwo) {
        ListNode tempNodeOne = headOne;
        ListNode tempNodeTwo = headTwo;
        ListNode virtualNode = new ListNode(-1);
        ListNode tempNodeThree = virtualNode;
        while (tempNodeOne != null && tempNodeTwo != null) {
            if (tempNodeOne.getVal() <= tempNodeTwo.getVal()) {
                // 把符合条件的节点挂到结果链表上.
                tempNodeThree.next = tempNodeOne;
                tempNodeOne = tempNodeOne.next;
            } else {
                tempNodeThree.next = tempNodeTwo;
                tempNodeTwo = tempNodeTwo.next;
            }
            tempNodeThree = tempNodeThree.next;
        }

        // 把某一条链表还剩下的节点挂在 结果链表上.
        if (tempNodeOne != null) {
            tempNodeThree.next = tempNodeOne;
        } else {
            tempNodeThree.next = tempNodeTwo;
        }

        return virtualNode.next;
    }

    /**
     * 给你⼀个链表数组，每个链表都已经按升序排列，请你将这些链表合并成⼀个升序链表，返回合并后的链
     * 表。
     * 输⼊：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     * 解释：链表数组如下：
     * [
     * 1->4->5,
     * 1->3->4,
     * 2->6
     * ]
     * 将它们合并到⼀个有序链表中得到。
     * 1->1->2->3->4->4->5->6
     * 思路: 与合并两条链表不同的是 如何每次得到k个节点中的最小节点.
     * 方法: 借助PriorityQueue这一数据结构.把k条链表的头节点添加到队列中.进行排序.
     * 先确定k条链表的 最小头节点minHeadNode
     * 然后把minHeadNode 挂在结果链表上
     * 接着把 minHeadNode.next节点添加到队列中.再次进行排序.
     * 循环执行.退出条件是?
     */
    public ListNode mergeKLists(ListNode[] lists) {

        PriorityQueue<ListNode> priorityQueue = new PriorityQueue<>(1, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode listNodeOne, ListNode listNodeTwo) {
                return listNodeOne.val - listNodeTwo.val;
            }
        });
        for (ListNode headNode : lists) {
            priorityQueue.offer(headNode);
        }

        ListNode virtualNode = new ListNode(-1);
        ListNode temp = virtualNode;

        while (!priorityQueue.isEmpty()) {
            ListNode minNode = priorityQueue.poll();
            temp.next = minNode;

            ListNode nextNode = minNode.next;
            // priorityQueue中不能存null,所以需要判断, 因此导致minNode不可能为空.
            if (nextNode != null) {
                priorityQueue.offer(nextNode);
            }

            temp = temp.next;
        }
        return virtualNode.next;
    }

    @Test
    public void testMergeKLists() {
        // lists = [[1,4,5],[1,3,4],[2,6]]
        // [1,1,2,3,4,4,5,6]
        ListNode headOne = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 4, 5));
        ListNode headTwo = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 3, 4));
        ListNode headThree = ListNodeUtils.generateListNodes(Lists.newArrayList(2, 6));
        ListNode listNode = mergeKLists(new ListNode[]{headOne, headTwo, headThree});
        ListNodeUtils.showListNodes(listNode);
    }

    @Test
    public void testHasCycle() {
        ListNode listNode1 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(0);
        ListNode listNode4 = new ListNode(-4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode2;
        //showListNodes(listNode1);
        System.out.println(hasCycle(listNode1));
    }

    /**
     * 给定⼀个链表，判断链表中是否有环，如果链表中存在环，则返回 true，否则返回 false。
     *
     * @param head 链表的头节点
     * @return boolean
     */
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) { // todo 注意下.
            fast = fast.next.next;
            slow = slow.next;
            // 快慢指针相遇,说明含有环
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给定⼀个链表，返回链表开始⼊环的第⼀个节点，如果链表⽆环，则返回 null（不允许修改给定的链表）。
     * 输⼊：head = [3,2,0,-4], pos = 1
     * 输出：返回索引为 1 的链表节点
     * 解释：链表中有⼀个环，其尾部连接到第⼆个节点。
     * 思路:
     * 还是使用快慢指针,先找到相遇点, 此时快指针走了2k的距离,慢指针走了k的距离.环的长度也为k
     * 设环的起点 到相遇点的距离为 x, 则 head节点 到环的起点为 k-x. 此时 相遇点 到 环的起点也为 k- x
     * 然后让慢指针回退到head节点.快慢指针以相同的速度移动,相遇的地方就是环起点.
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            // fast 遇到null 说明没有环.
            return null;
        }

        slow = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    @Test
    public void testDetectCycle() {
        ListNode listNode1 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(0);
        ListNode listNode4 = new ListNode(-4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode2;
        System.out.println(detectCycle(listNode1));
    }

    /**
     * 给你两个单链表的头节点 headA 和 headB，请你找出并返回两个单链表相交的起始节点。如果两个链表没
     * 有交点，返回 null。
     * 题⽬数据保证整个链式结构中不存在环，算法不能修改链表的原始结构。
     * 输⼊：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA =
     * 2, skipB = 3
     * 输出：Intersected at '8'
     * 解释：相交节点的值为 8（注意，如果两个链表相交则不能为 0）。
     * 从各⾃的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。
     * 在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     * <p>
     * 思路:
     * 1.本质上就是看是否可以找到相遇点,但是因为链表A和链表B 的长度不一致,导致如果同时分别遍历A,B
     * 则相同时刻,不一定会找到那个相遇点.
     * 所以需要进行特殊处理:
     * 将链表A和链表B 看成逻辑上的一条链路.
     * 即用指针tempOne 遍历链表A,如果遇到终点则立即 转到链表B的头结点.接着遍历
     * 用指针tempTwo 遍历链表B,如果遇到终点则立即 转到链表A的头结点.接着遍历
     * <p>
     * 退出条件为 tempOne == tempTwo
     * 这里有两种情况
     * 1.有相遇点,则tempOne == tempTwo =相遇点
     * 2.没有相遇点 tempOne == tempTwo =null
     */
    public ListNode getIntersectionNode(ListNode headOne, ListNode headTwo) {
        ListNode tempOne = headOne;
        ListNode tempTwo = headTwo;
        while (tempOne != tempTwo) {
            if (tempOne == null) {
                tempOne = headTwo;
            } else {
                tempOne = tempOne.next;
            }

            if (tempTwo == null) {
                tempTwo = headOne;
            } else {
                tempTwo = tempTwo.next;
            }
        }
        return tempOne;
    }

    @Test
    public void test() {
        // listA = [4,1,8,4,5], listB = [5,0,1,8,4,5]
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(5);
        ListNode listNode3 = new ListNode(0);

        ListNode listNodeCommon1 = new ListNode(1);
        ListNode listNodeCommon2 = new ListNode(8);
        ListNode listNodeCommon3 = new ListNode(4);
        ListNode listNodeCommon4 = new ListNode(5);

        System.out.println("验证: " + (listNode1.equals(listNodeCommon3)));
        listNode1.setNext(listNodeCommon1);
        listNode2.setNext(listNode3);
        listNode3.setNext(listNodeCommon1);

        listNodeCommon1.setNext(listNodeCommon2);
        listNodeCommon2.setNext(listNodeCommon3);
        listNodeCommon3.setNext(listNodeCommon4);

        System.out.println(getIntersectionNode(listNode1, listNode2));
    }

    ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        // 区间 [a, b) 包含 k 个待反转元素
        ListNode a, b;
        a = b = head;
        for (int i = 0; i < k; i++) {
            // 不足 k 个，不需要反转，base case
            if (b == null) {
                return head;
            }
            b = b.next;
        }
        // 反转前 k 个元素
        ListNode newHead = reverse(a, b);
        // 递归反转后续链表并连接起来
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    /**
     * 反转区间 [a, b) 的元素，注意是左闭右开
     */
    ListNode reverse(ListNode a, ListNode b) {
        ListNode pre, cur, nxt;
        pre = null;
        cur = a;
        // while 终止的条件改一下就行了
        while (cur != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        // 返回反转后的头结点
        return pre;
    }

    @Test
    public void testReverse() {
        ListNode head = ListNodeUtils.generateListNodes(Lists.newArrayList(1, 2, 3, 4, 5, 6));
        ListNode listNode = reverseKGroup(head, 2);
        ListNodeUtils.showListNodes(listNode);
    }

}
