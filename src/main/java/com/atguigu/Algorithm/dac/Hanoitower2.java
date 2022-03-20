package com.atguigu.Algorithm.dac;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/14 8:23
 */
public class Hanoitower2 {
    /**
     * 分治法是一种很重要的算法。字面上的解释是“分而治之”，就是把一个复杂的问题分成两个或更多的相同或相似的子问题，
     * 再把子问题分成更小的子问题……直到最后子问题可以简单的直接求解，原问题的解即子问题的解的合并。
     * 这个技巧是很多高效算法的基础，如排序算法(快速排序，归并排序)，傅立叶变换(快速傅立叶变换)……
     * 分治算法可以求解的一些经典问题
     * 二分搜索
     * 大整数乘法
     * 棋盘覆盖
     * 合并排序
     * 快速排序
     * 线性时间选择
     * 最接近点对问题
     * 循环赛日程表
     * 汉诺塔
     */
    // 分治法在每一层递归上都有三个步骤：
    // 分解：将原问题分解为若干个规模较小，相互独立，与原问题形式相同的子问题
    // 解决：若子问题规模较小而容易被解决则直接解，否则递归地解各个子问题
    // 合并：将各个子问题的解合并为原问题的解。
    public static void main(String[] args) {
        towerOfHanoiMove(2, 'a', 'b', 'c');
    }

    public static void towerOfHanoiMove(int hanoiTowerNum, char startTower, char middleTower, char endTower) {

        // 1.如果起始塔中只有一个盘子, 则只需要将盘子 从 startTower -> endTower
        // 2.起始塔中有>= 2个盘子, 则总是可以将盘子看成 两部分, 最下面的一个盘子 和上面的盘子
        // 2.1 先将 上面的盘子 从 startTower -> middleTower
        // 2.2 再将最下面的一个盘子 从 startTower -> endTower
        // 2.3 将上面的盘子 从 middleTower -> endTower
        if (hanoiTowerNum == 1) {
            System.out.println("第1个盘子从 " + startTower + "移动到" + endTower);
        } else {
            towerOfHanoiMove(hanoiTowerNum - 1, startTower, endTower, middleTower);
            System.out.println("第" + hanoiTowerNum + "盘子从 " + startTower + "移动到" + endTower);
            towerOfHanoiMove(hanoiTowerNum - 1, middleTower, startTower, endTower);
        }
    }
}
