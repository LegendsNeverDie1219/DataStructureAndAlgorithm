package com.atguigu.leetcode.ChapterOne.differencearray;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/17 10:47
 */
public class DifferenceArray {
    // 前面的前缀和的适用场景主要是 原始数组不会修改, 但是会频繁查询某个区间的累加和的情况
    // 本次要介绍的差分数组的适用场景主要是:  原始数组的某个区间会频繁修改的情况
    private int[] diffArray;

    /**
     * 根据原始数组,来构造一个差分数组.
     *
     * @param originArray 原始数组.
     */
    public DifferenceArray(int[] originArray) {
        diffArray = new int[originArray.length];
        diffArray[0] = originArray[0];
        for (int i = 1; i < originArray.length; i++) {
            diffArray[i] = originArray[i] - originArray[i - 1];
        }
        //System.out.println("根据原始数组得到的差分数组为:" + Arrays.toString(diffArray));
    }

    /**
     * 给闭区间 [i,j]增加value
     *
     * @param i     区间的起始索引
     * @param j     区间的终止索引
     * @param value 增加的value(可以为负数)
     */
    public void increase(int i, int j, int value) {
        // 相当于给区间[i,originArray.length-1]的元素都增加value
        diffArray[i] += value;
        // 相当于给区间[j+1,originArray.length-1]的元素再减去value,即不增不减
        if (j + 1 < diffArray.length) {
            diffArray[j + 1] -= value;
        }
    }

    public int[] getResultArray() {
        int[] resultArray = new int[diffArray.length];
        resultArray[0] = diffArray[0];
        for (int i = 1; i < diffArray.length; i++) {
            resultArray[i] = resultArray[i - 1] + diffArray[i];
        }
        return resultArray;
    }
}
