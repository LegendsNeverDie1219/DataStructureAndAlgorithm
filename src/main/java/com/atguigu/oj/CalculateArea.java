package com.atguigu.oj;

import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/8 21:20
 */
public class CalculateArea {
    /**
     * 输入样例1:
     * 4 10
     * <p>
     * 1 1
     * 2 1
     * 3 1
     * 4 -2
     * 输出样例1 :12
     * <p>
     * 输入样例2:
     * 2 4
     * <p>
     * 0 1
     * 2 -2
     * <p>
     * 输出样例: 4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int stopPoint = scanner.nextInt();

        int[][] operations = new int[row][2];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < 2; j++) {
                operations[i][j] = scanner.nextInt();
            }
        }
        scanner.close();

        long result = getMinArea(stopPoint, operations);
        System.out.println(result);
    }

    /**
     * 根据横坐标的终点, 已经对应的点的移动记录. 获取 x轴, 点的移动记录, x = stopPoint 包围成的最小面积
     *
     * @param stopPoint  横坐标的终点
     * @param operations 点的移动记录. x,offsetY
     * @return 包围的最小面积.
     */
    private static long getMinArea(int stopPoint, int[][] operations) {
        // 分解问题,横坐标每向右移动一格,就计算该格的面积  1*y的绝对值. 然后累加到minArea上.
        int minArea = 0;
        int y = 0;
        int operateIndex = 0;
        //
        for (int i = 0; i < stopPoint; i++) {
            if (operateIndex < operations.length) {
                int[] oneOperation = operations[operateIndex];
                int operateXxPos = oneOperation[0];
                // 在循环过程中,索引i  正好和指令中点的横坐标相等. 则更新y.同时该指令也执行完毕. 指针后移
                if (operateXxPos == i) {
                    y = y + oneOperation[1];
                    operateIndex++;
                }
            }
            minArea += Math.abs(y);
        }
        return minArea;
    }
}
