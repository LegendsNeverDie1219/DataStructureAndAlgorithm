package com.atguigu.datastructure.queue;

import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/12 17:32
 */
public class ArrayQueueDemo2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayQueue arrayQueue = new ArrayQueue(3);
        boolean loop = true;
        char c = ' ';
        while (loop) {
            System.out.println("a(add) 添加元素到队列");
            System.out.println("g(get) 从队列中获取元素");
            System.out.println("s(show) 显示队列中的所有元素");
            System.out.println("h(head) 显示队列中的第一个元素");
            System.out.println("e(exit) 退出");

            // String s = scanner.next();
            // System.out.println("start:"+ s + "end") ;
            if (scanner.hasNext()) {
                String s = scanner.nextLine();
                c = s.charAt(0);
            }
              // todo 注意 nextLine() 存在数组越界
            switch (c) {
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
                    }
                    break;
                }
                case 's': {
                    arrayQueue.showElements();
                    break;
                }
                case 'h': {
                    try {
                        int i = arrayQueue.showHeadElements();
                        System.out.printf("头部元素为: %d", i);
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

    private static class ArrayQueue {
        // 队列的容量
        private final int maxSize;
        // 队列的前指针,指向队列第一个元素的前一个位置
        private int front;
        // 队列的后指针, 指定队列的最后一个元素的位置.
        private int rear;
        private final int[] array;

        // 指定容量,初始化数组大小
        public ArrayQueue(int maxSize) {
            this.maxSize = maxSize;
            this.array = new int[maxSize];
            this.front = -1;
            this.rear = -1;
        }

        // 队列中存元素
        public void addElement(int element) {
            if (isFull()) {
                System.out.println("队列已满,无法添加");
                return;
            }
            rear++;
            array[rear] = element;
        }

        private boolean isFull() {
            return rear == maxSize - 1;
        }

        // 队列中取元素
        public int getElement() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空,不能取元素");
            }
            front++;
            return array[front];
        }

        private boolean isEmpty() {
            return front == rear;
        }

        // 显示队列中的元素
        public void showElements() {
            if (isEmpty()) {
                System.out.println("队列为空");
                return;
            }
            for (int j : array) {
                System.out.printf("%d\n", j);
            }
            for (int i = 0; i < array.length; i++) {
                System.out.printf("arr[%d] = %d \n", i, array[i]);
            }
        }

        // 显示队列中的第一个元素
        public int showHeadElements() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空,不能显示");
            }
            return array[front + 1];
        }
    }
}
