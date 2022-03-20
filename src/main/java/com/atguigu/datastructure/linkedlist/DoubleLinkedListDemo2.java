package com.atguigu.datastructure.linkedlist;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/12 21:58
 */
public class DoubleLinkedListDemo2 {
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
        //
        System.out.println("=========================================================================");
        HeroNode newHeroNode = new HeroNode(20, "hds", "一眼千山白驹掠影");
        singleLinkedList.updateNode(newHeroNode);
        singleLinkedList.listNodes();
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
            heroNode.setPre(temp);
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
                // 如果插入的是最后一个位置.此时temp 指向最后一个节点, 则不需要设置temp.getNext()的前指针
                if(temp.getNext() != null) {
                    temp.getNext().setPre(heroNode);
                }

                temp.setNext(heroNode);
                heroNode.setPre(temp);
            } else {
                System.out.printf("没有找到指定节点为%d的待插入节点\n", heroNode.getNo());
            }
        }

        public void deleteNode(int no) {
            // 判断当前链表是否为空
            if (head.next == null) {// 空链表
                System.out.println("链表为空，无法删除");
                return;
            }
            // `1.遍历找到待删除节点temp
            // 2.然后 让待删除节点的上一个节点temp.getPre() 的next域 -> 待删除节点的下一个节点temp.getNext().
            HeroNode temp = head.getNext();
            // 为false 说明没有找到待删除的节点
            boolean flag = false;
            while (true) {
                // 如果到达链表的尾部时退出,temp 指向链表的最后一个节点的下一个位置
                if (temp == null) {
                    break;
                }
                if (temp.getNo() == no) {
                    flag = true;
                    break;
                }
                // temp 指针后移
                temp = temp.getNext();
            }

            if (flag) {
                temp.getPre().setNext(temp.getNext());
                if (temp.getNext() != null) {
                    temp.getNext().setPre(temp.getPre());
                }

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
            HeroNode temp = head;
            while (temp.getNext() != null) {
                System.out.println(temp.getNext());
                temp = temp.getNext();
            }
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
        // pre域的属性, 记录指向的上一个节点.
        private HeroNode pre;

        public HeroNode getNext() {
            return next;
        }

        public void setNext(HeroNode next) {
            this.next = next;
        }

        public HeroNode getPre() {
            return pre;
        }

        public void setPre(HeroNode pre) {
            this.pre = pre;
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
