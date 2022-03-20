package com.atguigu.datastructure.stack;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/14 22:16
 */
public class LinkedListStackDemo2 {
    public static void main(String[] args) {
        LinkListStack stack = new LinkListStack();
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个节点编号");
                    int no = scanner.nextInt();
                    System.out.println("请输入一个节点名称");
                    String name = scanner.next();
                    stack.push(new Node(no, name));
                    break;
                case "pop":
                    try {
                        Node node = stack.pop();
                        System.out.printf("出栈的数据是 %s\n", node);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~~");
    }

    //用链表模拟栈
    private static class LinkListStack {
        private final Node headNode = new Node(0, "");//创建头节点
        private Node top = headNode;//当前栈顶节点，一开始指向头节点

        //向栈中添加元素
        public void push(Node node) {
            //将新节点加入链表
            top.setNext(node);
            top = top.getNext();
        }

        public Node pop() {
            if (headNode.getNext() == null) {
                throw new RuntimeException("栈中没有元素了.无法取出1219");
            }
            Node temp = headNode;
            while (temp.getNext() != null) {
                if (temp.getNext().getNo() == top.getNo()) {
                    break;
                }
                temp = temp.getNext();
            }
            // 暂时保存出栈的节点.
            Node popNode = top;

            // top 指针前移
            temp.setNext(null);
            top = temp;

            return popNode;
        }

        public void list() {
            if (headNode.getNext() == null) {
                System.out.println("栈空，无法显示");
                return;
            }
            //从链表尾倒着读取元素
            Node tempNode = headNode;
            while (tempNode.getNext() != null) {
                System.out.println(tempNode.getNext());
                tempNode = tempNode.getNext();
            }
        }
    }

    @Setter
    @Getter
    private static class Node {
        private int no;
        private String name;
        private Node next;

        public Node(int no, String name) {
            this.no = no;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

