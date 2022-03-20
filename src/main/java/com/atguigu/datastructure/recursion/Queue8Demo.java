package com.atguigu.datastructure.recursion;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/18 8:07
 */
public class Queue8Demo {
    public static void main(String[] args) {
        Queue queue8 = new Queue(8);

        queue8.check(0);
        System.out.println(queue8.count);
    }

    private static class Queue {
        //定义一个max表示共有多少个皇后
        private final int maxSize;
        //定义数组array, 保存皇后放置位置的结果,比如 arr = {0 , 4, 7, 5, 2, 6, 1, 3}
        private final int[] array;
        private int count;

        public Queue(int maxSize) {
            this.maxSize = maxSize;
            array = new int[maxSize];
        }

        //编写一个方法，放置第n个皇后
        public void check(int queueNo) {
            // int count = 0;
            if (queueNo == maxSize) { // 说明前8个皇后已经放置好了.
                printArray();
                return;
            }
            // 第n个皇后摆放的列的选择.0~7列
            for (int i = 0; i < maxSize; i++) {
                array[queueNo] = i;
                // 如果不冲突, 则摆放下一个皇后
                if (!judgementConflict(queueNo)) {
                    check(queueNo + 1);
                }
                // 否则, 移动下一列, 继续判断冲突 array[queueNo] = i;
            }
            // return true;
        }
        // 判断是否冲突
        private boolean judgementConflict(int queueNo) {
            // 即第n个皇后和前n-1个皇后进行位置比较, 如果处于处于一列,或者同一斜线,则冲突.
            for (int i = 0; i < queueNo; i++) {
                if (array[queueNo] == array[i] || Math.abs(queueNo - i) == Math.abs(array[queueNo] - array[i])) {
                    return true;
                }
            }
            return false;
        }
        // 打印结果
        public void printArray() {
            count++;
            for (int j : array) {
                System.out.printf("%d ", j);
            }
            System.out.println();
        }
    }
}
