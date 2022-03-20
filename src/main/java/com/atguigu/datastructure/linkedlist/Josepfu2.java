package com.atguigu.datastructure.linkedlist;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/13 21:21
 */
public class Josepfu2 {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoys(5);
        circleSingleLinkedList.showBoys();
        circleSingleLinkedList.countBoys(1,2,5);
    }

    private static class CircleSingleLinkedList {
        // 循环单向链表的第一个节点.
        private Boy first = null;

        public void addBoys(int nums) {
            if (nums < 1) {
                System.out.printf("nums: %d 的个数有误,无法添加", nums);
                return;
            }
            // 定义辅助指针,帮助构建单向循环链表
            Boy curBoy = null;
            for (int i = 1; i <= nums; i++) {
                Boy boy = new Boy(i);
                // 加入的是第一个小孩
                if (i == 1) {
                    // 把该小孩赋值给first. first指针 指向该小孩
                    first = boy;
                    first.setNext(first); // 形成环 first.setNext(boy) 也可以.
                    curBoy = first;
                } else {
                    curBoy.setNext(boy);
                    boy.setNext(first);
                    curBoy = curBoy.getNext();
                }
            }
        }

        public void showBoys() {
            if (first == null) {
                System.out.println("当前链表中没有小孩,无法遍历");
                return;
            }
            Boy curBoy = first;
            while (true) {
                System.out.println(curBoy);
                if (curBoy.getNext() == first) {
                    break;
                }
                curBoy = curBoy.getNext();
            }
        }

        /**
         * countBoys
         *
         * @param k     从第几个小孩开始报数
         * @param count 表示数几下
         * @param nums  小孩的总个数
         */
        public void countBoys(int k, int count, int nums) {
            if (first == null || k < 1 || k > nums) {
                System.out.printf("k: %d 不合法,无法报数.", k);
                return;
            }
            // 1.定义前后指针,并初始化
            // 前指针 first 初始化时 指向第一个节点
            // 后指针 helper 初始化时 指向最后一个节点
            Boy helper = first;
            // 退出时 指向最后一个节点.
            while(helper.getNext() != first) {
                helper = helper.getNext();
            }
            // 2.前指针指向要报数的小孩, 即前后指针移动k-1下.
           for(int i = 0; i < k -1; i++) {
               first = first.getNext();
               helper = helper.getNext();
           }
            while (true) {
                if(first == helper) {
                    System.out.println("说明圈中只有一个节点. 计数结束");
                    break;
                }
                //第n轮
                // 3.开始计数count-1次, 修改指针指向
                for(int i = 0; i < count -1; i++) {
                    first = first.getNext();
                    helper = helper.getNext();
                }
                // 此刻curBoy 指向 出圈的小孩
                //这时first指向的节点，就是要出圈的小孩节点
                System.out.printf("小孩%d出圈\n", first.getNo());
                first = first.getNext();
                // 待删除节点的上一个节点的next 域  指向待删除节点的下一个节点.
                helper.setNext(first);
            }
            System.out.printf("最后留在圈中的小孩编号%d \n", first.getNo());
        }
    }

    @Setter
    @Getter
    private static class Boy {
        private int no;
        private Boy next;

        public Boy(int no) {
            this.no = no;
        }

        @Override
        public String toString() {
            return "Boy{" +
                    "no=" + no +
                    '}';
        }
    }
}
