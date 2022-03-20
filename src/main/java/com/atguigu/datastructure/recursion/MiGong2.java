package com.atguigu.datastructure.recursion;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/17 23:07
 */
public class MiGong2 {
    public static void main(String[] args) {
        int[][] map = new int[8][7];
        setMap(map);

        printMap(map);

        //使用递归回溯给小球找路
        int i = 1;
        int j = 1;
        boolean result = setWay(map, i, j);
        System.out.println("探路结果: " + result);

        printMap(map);
    }

    /**
     * @param map map 地图
     * @param i   小球起始行位置
     * @param j   小球起始列位置
     * @return boolean
     */
    // 先定2条规则,
    //  0 代表该位置没有走过, 1代表是墙, 2 代表是可以走通, 3 代表无法走通
    //  按照 下 右 上 左的顺序去走.
    private static boolean setWay(int[][] map, int i, int j) {
        // 如果目的地位置为2 ,说明小球找到路了.
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {
                // 玩一把 ,按照 下 右 上 左的顺序去走.
                // 假定可以该位置可以走通, (如果走不通,,到时候还会回溯成3)
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) {
                    return true;
                } else if (setWay(map, i, j + 1)) {
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    return true;
                } else if (setWay(map, i, j - 1)) {
                    return true;
                } else {
                    // 下, 右, 上, 左 都不通
                    map[i][j] = 3;
                    return false;
                }
            } else {
                // 有三种情况,
                // 1是墙, 说明走不通,返回false
                // 2 是已经走过, 没有必要再走, 返回fasle
                // 3 走不通,返回false
                return false;
            }
        }
    }

    private static void setMap(int[][] map) {
        // 填充第一行和最后一行
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        // 填充第一列和最后一列
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        //设置挡板, 1 表示
        map[3][1] = 1;
        map[3][2] = 1;
//		map[1][2] = 1;
//		map[2][2] = 1;
    }

    private static void printMap(int[][] map) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
}

