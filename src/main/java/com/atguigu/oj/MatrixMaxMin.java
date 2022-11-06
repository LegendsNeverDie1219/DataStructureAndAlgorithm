package com.atguigu.oj;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/16 22:53
 */
public class MatrixMaxMin {
    /**
     * 现给定一个n*m的矩阵,请先找出每列元素的最大值,合计m个.然后输出这m个值中的最小值.
     * 输入:
     * 首行为两个整数n,m表示有n行m列
     * 接下来n行,每行m个元素
     * 输出:
     * 整数. 表示所求的值.
     * <p>
     * 3 2
     * <p>
     * 1 2
     * 3 4
     * 2 4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        int[][] matrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
        int result = getTheMinWithColMaxs(matrix);
        System.out.println(result);
    }

    private static int getTheMinWithColMaxs(int[][] matrix) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Integer::compare);
        for (int j = 0; j < matrix[0].length; j++) {
            int maxValue = Integer.MIN_VALUE;
            // 先找第一列的max. 在找第二列的最大值. 前提: 每行的列数相等.

            // 每一列的值.
            for (int i = 0; i < matrix.length; i++) {
                maxValue = Math.max(matrix[i][j], maxValue);
            }
            priorityQueue.offer(maxValue);
        }

        return priorityQueue.poll();
    }
}


