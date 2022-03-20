package com.atguigu.Algorithm.dynamic;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/14 22:29
 */
public class BackPackProblem {
    public static void main(String[] args) {
        int[] weightArr = new int[]{0, 1, 4, 3};
        int[] valueArr = new int[]{0, 1500, 3000, 2000};
        String[] goodsArr = new String[]{"空", "吉它", "音响", "电脑"};
        int backPackCapacity = 4;

        int[][] maxValueArr = new int[valueArr.length][backPackCapacity + 1];
        //为了记录放入商品的情况，我们定一个二维数组
        int[][] paths = new int[valueArr.length][backPackCapacity + 1];
        // 填充第一行 和第一列
        // 即没有任何东西可以放入,背包容量为j(0-4)时 的最大价值
        // 可以放入i(0-3)种类型的商品,背包容量为0时 的最大价值
        for (int j = 0; j < maxValueArr[0].length; j++) {
            maxValueArr[0][j] = 0;
        }

        for (int i = 0; i < maxValueArr.length; i++) {
            maxValueArr[i][0] = 0;
        }

        for (int i = 1; i < maxValueArr.length; i++) { // 商品i为[1,3]
            for (int j = 1; j < maxValueArr[0].length; j++) { // 容量j为[1, 4]
                // 第i件商品的重量 > 容量j 沿用上一个单元格的最大价值
                if (weightArr[i] > j) {
                    // 前i件商品放入容量为j 的背包中的最大价值
                    maxValueArr[i][j] = maxValueArr[i - 1][j];
                } else {
                    int previousCellMaxValue = maxValueArr[i - 1][j];
                    int putCurrentGoodValue = valueArr[i] + maxValueArr[i - 1][j - weightArr[i]];
                    if (putCurrentGoodValue >= previousCellMaxValue) {
                        maxValueArr[i][j] = putCurrentGoodValue;
                        paths[i][j] = 1;
                    } else {
                        maxValueArr[i][j] = previousCellMaxValue;
                    }
                }
            }
        }
        // 打印表格信息
        for (int i = 0; i < maxValueArr.length; i++) {
            for (int j = 0; j < maxValueArr[0].length; j++) {
                System.out.printf("%d\t", maxValueArr[i][j]);
            }
            System.out.println();
        }

        // 倒序取出数据
        int goodIndex = paths.length - 1; // 3
        int backPackIndex = paths[0].length - 1; // 4
        while (goodIndex >= 0 && backPackIndex >= 0) {
            // 如果满足条件,则从背包中取出该商品
            if (paths[goodIndex][backPackIndex] == 1) {
                backPackIndex = backPackIndex - weightArr[goodIndex];
                System.out.println("第" + goodIndex + "件商品从背包中取出, " + "商品名称为: " + goodsArr[goodIndex]);
            }
            goodIndex--;
        }

    }
}
