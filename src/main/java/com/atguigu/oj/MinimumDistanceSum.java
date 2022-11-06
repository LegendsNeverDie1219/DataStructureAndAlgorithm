package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/1 9:33
 */
public class MinimumDistanceSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] gridMatrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridMatrix[i][j] = scanner.nextInt();
            }
        }
        scanner.close();

        System.out.println(nearestWareHouse(gridMatrix));
    }

    private static int nearestWareHouse(int[][] gridMatrix) {
        int minPathSum = 0;
        // 根据标记矩阵(0 标记的是仓库, 1 标记的是便利店, -1 标记的是障碍) 获取仓库坐标, 障碍物坐标.并标记该坐标已经被访问
        // 如果是仓库坐标.则加入到坐标延伸队列中.(即以该坐标向周围四个方向延伸, 看是否存在 合法且没有访问的便利店据点(索引不越界 && 没有被访问过)
        // 如果就存在,则将该便利店据点加入到路径求和队列中,并且标记为已经访问. result+step(step=1 其中的路径和是以仓库为基准点的.)
        // 并且步长要自增一. 为下一次延伸做准备.
        // 仓库据点全部执行完之后,再以 上一次得到的  合法且没有访问的便利店据点 为 中心点 向四个方向延伸. 看是否存在  合法且没有访问的便利店据点
        // ...
        boolean[][] isVisitedArr = new boolean[gridMatrix.length][gridMatrix[0].length];
        Queue<int[]> coordinateExtensionQueue = new LinkedList<>();
        for (int i = 0; i < gridMatrix.length; i++) {
            for (int j = 0; j < gridMatrix[i].length; j++) {
                if (gridMatrix[i][j] == 0) {
                    isVisitedArr[i][j] = true;
                    coordinateExtensionQueue.offer(new int[]{i, j});
                }
                if (gridMatrix[i][j] == -1) {
                    isVisitedArr[i][j] = true;
                }
            }
        }

        int step = 1;
        while (!coordinateExtensionQueue.isEmpty()) {
            int size = coordinateExtensionQueue.size(); //todo 注意 每一次的队列的size要事前提取出来.因为在循环中还会添加元素,


            for (int i = 0; i < size; i++) {
                int[] positionCoordinate = coordinateExtensionQueue.poll();
                // [1,0] [0,2]
                int xPosition = positionCoordinate[0];
                int yPosition = positionCoordinate[1];


                // todo 寻找弹出的当前节点 的邻接/有效(!=null,不越界)/ 没有访问过的节点.
                // 向上延伸
                if (xPosition - 1 >= 0 && !isVisitedArr[xPosition - 1][yPosition]) {
                    coordinateExtensionQueue.offer(new int[]{xPosition - 1, yPosition});
                    isVisitedArr[xPosition - 1][yPosition] = true;
                    minPathSum = minPathSum + step;
                }
                // 向左延伸
                if (yPosition - 1 >= 0 && !isVisitedArr[xPosition][yPosition - 1]) {
                    coordinateExtensionQueue.offer(new int[]{xPosition, yPosition - 1});
                    isVisitedArr[xPosition][yPosition - 1] = true;
                    minPathSum = minPathSum + step;
                }
                // 向下延伸
                if (xPosition + 1 < gridMatrix.length && !isVisitedArr[xPosition + 1][yPosition]) {
                    coordinateExtensionQueue.offer(new int[]{xPosition + 1, yPosition});
                    isVisitedArr[xPosition + 1][yPosition] = true;
                    minPathSum = minPathSum + step;
                }

                // 向右延伸
                if (yPosition + 1 < gridMatrix[0].length && !isVisitedArr[xPosition][yPosition + 1]) {
                    coordinateExtensionQueue.offer(new int[]{xPosition, yPosition + 1});
                    isVisitedArr[xPosition][yPosition + 1] = true;
                    minPathSum = minPathSum + step;
                }
            }




            step++;
        }
        return minPathSum;
    }
}
