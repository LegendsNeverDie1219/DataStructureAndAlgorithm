package com.atguigu.datastructure.queue;

import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/12 20:04
 */
public class CircleArrayQueueDemo2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 最大容量 4 实际只能存3个
        CircleArrayQueue arrayQueue = new CircleArrayQueue(4);
        boolean loop = true;
        char key = ' ';
        while (loop) {
            System.out.println("a(add) 添加元素到队列");
            System.out.println("g(get) 从队列中获取元素");
            System.out.println("s(show) 显示队列中的所有元素");
            System.out.println("h(head) 显示队列中的第一个元素");
            System.out.println("e(exit) 退出");

            key = scanner.next().charAt(0);
            switch (key) {
                case 'e': {
                    // 改变标识, 为退出循环准备
                    scanner.close();
                    loop = false;
                    // 推出switch case
                    break;
                }
                case 'a': {
                    System.out.println("请输入一个元素");
                    int element = scanner.nextInt();
                    arrayQueue.addElement(element);
                    break;
                }
                case 'g': {
                    try {
                        int element = arrayQueue.getElement();
                        System.out.printf("取出的数据是%d\n", element);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.printf("取出的数据是%d\n", null);
                    }
                    break;
                }
                case 's': {
                    arrayQueue.showElements();
                    break;
                }
                case 'h': {
                    try {
                        int i = arrayQueue.showHeadElement();
                        System.out.printf("头部元素为: %d\n", i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    private static class CircleArrayQueue {
        // 最大容量,其中只能存 3个元素.要留出一个位置
        private int maxSize;
        // 前指针, 指向队列中的第一个元素
        private int front;
        // 后指针,指向队列中的最后一个元素的后一个位置.
        private int rear;
        private int[] array;

        public CircleArrayQueue(int maxSize) {
            this.maxSize = maxSize;
            this.array = new int[maxSize];
        }

        // 添加元素到尾部
        public void addElement(int element) {
            if (isFull()) {
                System.out.println("队列已满,不能再添加了");
                return;
            }
            array[rear] = element;
            // rear++;
            rear = (rear + 1) % maxSize;
        }

        private boolean isFull() {
            return (rear + 1) % maxSize == front;
        }

        // 获取头部的元素
        public int getElement() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空,不能再获取了");
            }
            int value = array[front];
            front = (front + 1) % maxSize;
            return value;
        }

        private boolean isEmpty() {
            return rear == front;
        }

        // 查看队列中的所有元素
        public void showElements() {
            if (isEmpty()) {
                System.out.println("队列为空");
                return;
            }
            for (int i = front; i < front + size(); i++) {
                System.out.printf("arr[%d]: %d \n", i % maxSize, array[i % maxSize]);
            }
        }

        public int size() {
            return (rear + maxSize - front) % maxSize;
        }

        // 查看队列中的第一个元素
        public int showHeadElement() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空,没有头元素");
            }
            return array[front];
        }
    }
}
