package com.atguigu.datastructure.linkedlist;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/12 21:58
 */
public class SingleLinkedListDemo2 {
    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        singleLinkedList.addNode(hero1);
        singleLinkedList.addNode(hero2);
        singleLinkedList.addNode(hero3);
        singleLinkedList.addNode(hero4);
        singleLinkedList.listNodes();

        System.out.println("=========================================================================");
        singleLinkedList.deleteNode(2);
        singleLinkedList.listNodes();

        System.out.println("=========================================================================");
        singleLinkedList.addNodeByOrder(hero2);
        singleLinkedList.listNodes();

        System.out.println("=========================================================================");
        HeroNode newHeroNode = new HeroNode(20, "hds", "一眼千山白驹掠影");
        singleLinkedList.updateNode(newHeroNode);
        singleLinkedList.listNodes();
    }

    @Test
    public void testMethod() {
        SingleLinkedList singleLinkedList = constractSingleLinkedList();
        HeroNode head = singleLinkedList.getHead();

        System.out.println(getLengthOfSingleLinkedList(head));

        System.out.println(findKthLastNodeOfSingleLinkedList(head, 5));

        System.out.println(findKthLastNodeOfSingleLinkedList2(head, 5));

        //reverseSingleLinkedList(head);

        //printSingleLinkedList(head);

        printSingleLinkedList2(head);

    }

    // 求单链表中有效节点的个数(不带头结点)
    public int getLengthOfSingleLinkedList(HeroNode head) {
        int length = 0;
        HeroNode cur = head;
        // cur指针 指向最后一个节点 退出
        while (cur.getNext() != null) {
            length++;
            cur = cur.getNext();
        }
        return length;
    }

    // 查找单链表中的倒数第k个结点 【新浪面试题】(两种方法)
    public HeroNode findKthLastNodeOfSingleLinkedList(HeroNode head, int k) {
        // 1. 获取链表的长度 length
        // 2.校验k
        // 3.遍历链表到 length - k的位置
        int length = getLengthOfSingleLinkedList(head);
        if (k <= 0 || k > length) {
            return null;
        }
        // todo 如果是cur初始化时 指向head,则只需要移动length- k 次,否则需要移动 length-k+1
        HeroNode cur = head.getNext();
        for (int i = 0; i < length - k; i++) {
            cur = cur.getNext();
        }
        return cur;
    }

    // 快慢指针
    public HeroNode findKthLastNodeOfSingleLinkedList2(HeroNode head, int k) {
        // 1.校验k
        // 2. 快指针先走k步
        // 3.然后快慢指针一起以相同的速度开始走,直到快指针到达链表尾部 此刻满指针指向的就倒数第k个元素
        int length = getLengthOfSingleLinkedList(head);
        if (k <= 0 || k > length) {
            return null;
        }

        HeroNode fast = head;
        HeroNode slow = head;
        //快指针先走k步
        while (k-- != 0) {
            fast = fast.getNext();
        }
        // todo 指针指向最后一个节点的下一个位置 结束
        while (fast != null) {
            fast = fast.getNext();
            slow = slow.getNext();
        }
        return slow;
    }

    // 单链表的反转【腾讯面试题]
    public void reverseSingleLinkedList(HeroNode head) {
        // 1.先定义一个临时的reverseHead头节点
        // 2.然后遍历当前链表,每遍历到一个节点,就将其摘出,挂到reverseHead的后面
        // 3.最后头节点 重新指向反转后的链表 head.next = reverseHead.next;
        // 当前链表为空,或者只有一个元素.都不用反转.
        if (head.getNext() == null || head.getNext().getNext() == null) {
            return;
        }
        HeroNode reverseHead = new HeroNode(0, "", "");
        // cur指针 初始化时指向第一个有效节点
        HeroNode cur = head.next;
        while (cur != null) {
            // 当cur指针 指向最后一个节点的后一个位置时, 退出
            HeroNode next = cur.getNext();

            // 更新该节点的next域, 让该节点指向新的链表的最前端.
            cur.setNext(reverseHead.getNext());
            // 挂到reverseHead的后面
            reverseHead.setNext(cur);

            cur = next;
        }
        head.setNext(reverseHead.getNext());
        listNode(head);
    }

    // 从尾到头打印单链表 【百度，要求方式1：反向遍历 。 方式2：Stack栈】
    public void printSingleLinkedList(HeroNode head) {
        reverseSingleLinkedList(head);

        listNode(head);
    }

    private static void listNode(HeroNode head) {
        HeroNode temp = head;
        while (temp.getNext() != null) {
            System.out.println(temp.getNext());
            temp = temp.getNext();
        }
    }

    public void printSingleLinkedList2(HeroNode head) {
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.getNext();
        while (cur != null) {
            stack.push(cur);
            cur = cur.getNext();
        }

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    private SingleLinkedList constractSingleLinkedList() {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        singleLinkedList.addNode(hero1);
        singleLinkedList.addNode(hero2);
        singleLinkedList.addNode(hero3);
        singleLinkedList.addNode(hero4);

        return singleLinkedList;
    }


    // 带头结点的单链表
    private static class SingleLinkedList {
        private final HeroNode head = new HeroNode(0, "", "");

        public HeroNode getHead() {
            return head;
        }

        public void addNode(HeroNode heroNode) {
            // 1.遍历找到链表的最后一个节点lastNode
            // 2.最后一个节点的next域 指向添加的节点 lastNode.next = heroNode
            // 定义辅助指针,首先指向头结点
            HeroNode temp = head;
            while (true) {
                if (temp.getNext() == null) {
                    break;
                }
                // temp指针后移
                temp = temp.getNext();
            }
            temp.setNext(heroNode);
        }

        // 插入节点, 如果该节点存在,则插入失败(节点的唯一标识就是 no)
        public void addNodeByOrder(HeroNode heroNode) {
            // 1.遍历链表 找到待插入节点的上一个节点. temp
            // 2. 待插入节点的next域 指向 temp.next  ,temp.next 指向heroNode
            HeroNode temp = head;
            boolean flag = false;
            while (true) {
                if (temp.getNext() == null) {
                    break;
                }
                // 当前节点的no 大于 要插入的节点的no, 位置找到，就在temp的后面插入
                if (temp.getNext().getNo() > heroNode.getNo()) {
                    flag = true;
                    break;
                } else if (temp.getNext().getNo() == heroNode.getNo()) {
                    //说明编号存在
                    break;
                }
                temp = temp.getNext();
            }
            if (flag) {
                heroNode.setNext(temp.getNext());
                temp.setNext(heroNode);
            } else {
                System.out.printf("没有找到指定节点为%d的待插入节点\n", heroNode.getNo());
            }
        }

        public void deleteNode(int no) {
            // `1.遍历找到待删除节点的 上一个节点temp
            // 2.然后 让待删除节点的上一个节点temp 的next域 -> 待删除节点的下一个节点.  temp .next = temp.next.next;
            HeroNode temp = head;
            // 为false 说明没有找到待删除的节点
            boolean flag = false;
            while (true) {
                // 到达链表的最后一个节点
                if (temp.getNext() == null) {
                    break;
                }
                if (temp.getNext().getNo() == no) {
                    flag = true;
                    break;
                }
                // temp 指针后移
                temp = temp.getNext();
            }

            if (flag) {
                temp.setNext(temp.next.next);
            } else {
                System.out.printf("没有找到指定节点为%d的待删除节点\n", no);
            }
        }

        public void updateNode(HeroNode newHeroNode) {
            // 1. 遍历找到需要需要的节点
            // 2.进行名称和昵称的修改(其中编号不能修改)
            HeroNode temp = head.getNext();
            boolean flag = false;
            while (true) {
                if (temp == null) {
                    break;
                }
                if (temp.getNo() == newHeroNode.getNo()) {
                    flag = true;
                    break;
                }
                // 指针后移
                temp = temp.getNext();
            }

            if (flag) {
                temp.setName(newHeroNode.getName());
                temp.setNickName(newHeroNode.getNickName());
            } else {
                System.out.printf("没有找到指定节点为%d的待修改节点\n", newHeroNode.getNo());
            }
        }

        public void listNodes() {
            listNode(head);
        }
    }

    private static class HeroNode {
        // data域的 三个属性
        @Getter
        private final int no;
        @Setter
        @Getter
        private String name;
        @Setter
        @Getter
        private String nickName;
        // next域的属性 记录指向的下一个节点.
        private HeroNode next;

        public HeroNode getNext() {
            return next;
        }

        public void setNext(HeroNode next) {
            this.next = next;
        }

        public HeroNode(int no, String name, String nickName) {
            this.no = no;
            this.name = name;
            this.nickName = nickName;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickName='" + nickName + '\'' +
                    '}';
        }
    }
}
