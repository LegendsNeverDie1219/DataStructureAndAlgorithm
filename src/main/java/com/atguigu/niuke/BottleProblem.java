package com.atguigu.niuke;

import java.util.Scanner;

/**
 * BottleProblem
 *
 * @author Administrator
 * @date 2022/3/26 11:55
 */
public class BottleProblem {
    /**
     * 某商店规定：三个空汽水瓶可以换一瓶汽水，允许向老板借空汽水瓶（但是必须要归还）。
     * 小张手上有n个空汽水瓶，她想知道自己最多可以喝到多少瓶汽水。
     * 数据范围：输入的正整数满足
     * <p>
     * 注意：本题存在多组输入。输入的 0 表示输入结束，并不用输出结果。
     * <p>
     * 输入描述:
     * 输入文件最多包含 10 组测试数据，每个数据占一行，仅包含一个正整数 n（ 1<=n<=100 ），表示小张手上的空汽水瓶数。n=0 表示输入结束，你的程序不应当处理这一行。
     * <p>
     * <p>
     * 输出描述:
     * 对于每组测试数据，输出一行，表示最多可以喝的汽水瓶数。如果一瓶也喝不到，输出0。
     * <p>
     * <p>
     * 输入例子1:
     * 3
     * 10
     * 81
     * 0
     * <p>
     * 输出例子1:
     * 1
     * 5
     * 40
     * <p>
     * 例子说明1:
     * 样例 1 解释：用三个空瓶换一瓶汽水，剩一个空瓶无法继续交换
     * 样例 2 解释：用九个空瓶换三瓶汽水，剩四个空瓶再用三个空瓶换一瓶汽水，剩两个空瓶，向老板借一瓶汽水喝完得三个空瓶换一瓶汽水还给老板
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int bottleNumber = scanner.nextInt();
            if (bottleNumber == 0) {
                scanner.close();
                break;
            }

            int sumSodaNumber = getSumSodaNumber(bottleNumber);
            System.out.println(sumSodaNumber);
        }
    }

    private static int getSumSodaNumber(int bottleNumber) {
        // 汽水数量
        int sumSodaNumber = 0;
        while (bottleNumber >= 3) {
            // 第一轮:
            // 汽水 = 0 + 10/ 3 = 3
            // 瓶子 = 10/3 + 10%3 = 4
            //第2轮
            // 汽水 = 3 + 4/3 = 4
            // 瓶子 = 4/3 + 4%3 = 2
            int thisRoundSodaNumber = bottleNumber / 3;
            sumSodaNumber = sumSodaNumber + thisRoundSodaNumber;

            int oldBottleNumber = bottleNumber % 3;
            bottleNumber = thisRoundSodaNumber + oldBottleNumber;
            if (bottleNumber == 2) {
                bottleNumber++;
            }
        }
        return sumSodaNumber;
    }

}
