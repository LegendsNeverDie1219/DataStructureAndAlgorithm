package com.atguigu.leetcode.ChapterOne.arraydoublepointer.other;

import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/30 21:18
 */
public class TheContainerThatHoldsTheMostWater {
    public static void main(String[] args) {
        int[] heightArr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int amountOfWater = catchRain(heightArr);
        System.out.println(amountOfWater);
    }

    @Test
    public void testCatchRinOne() {
        int[] heightArr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int amountOfWater = catchRain(heightArr);
        System.out.println(amountOfWater);
    }

    @Test
    public void testCatchRinTwo() {
        int[] heightArr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int amountOfWater = catchRainTwo(heightArr);
        System.out.println(amountOfWater);
    }

    @Test
    public void testCatchRinThree() {
        int[] heightArr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int amountOfWater = catchRainThree(heightArr);
        System.out.println(amountOfWater);
    }
    @Test
    public void testTheContainerThatHoldsTheMostWater() {
        int[] heightArr = new int[]{1,8,6,2,5,4,8,3,7};
        int amountOfWater = theContainerThatHoldsTheMostWater(heightArr);
        System.out.println(amountOfWater);

    }
    public int theContainerThatHoldsTheMostWater(int[] heightArr) {
        int left = 0;
        int right = heightArr.length -1;
        int maxArea  = 0;
        // todo
        while (left <=right) {
            int thresholdHeight = Math.min(heightArr[left],heightArr[right]);
            int currentArea = thresholdHeight*(right-left);
            maxArea = Math.max(maxArea,currentArea);
            if (heightArr[left] < heightArr[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

    /**
     * 使用双指针解法
     *
     * @param heightArr heightArr
     * @return int
     */
    private int catchRainThree(int[] heightArr) {
        int left = 0;
        int right = heightArr.length - 1;
        int amountOfWater = 0;
        int leftMaxHeight = 0;
        int rightMaxHeight = 0;
        while (left <= right) {
            // [0,left]范围内的最大值.即left位置左边最高柱子的高度.
            leftMaxHeight = Math.max(heightArr[left], leftMaxHeight);
            // [right,length-1] 范围内的最大值,即right位置右边最高柱子的高度.
            rightMaxHeight = Math.max(heightArr[right], rightMaxHeight);

            // 只需要要找到位置left/right 左右两边最小的 那个maxHeight
            if (leftMaxHeight < rightMaxHeight) {
                amountOfWater = amountOfWater + leftMaxHeight - heightArr[left];
                left++;
            } else {
                amountOfWater = amountOfWater + rightMaxHeight - heightArr[right];
                right--;
            }
        }
        return amountOfWater;
    }

    /**
     * 之前的暴力解法,是要在循环中计算位置i的 leftMax,rightMax都求出来,
     * 现在需要做的是,提前计算出位置i的leftMax,rightMax 备忘录法
     * 用两个数组leftMaxArr,rightMaxArr 来记录[0,arr.length()-1]范围内的数据.
     *
     * @param heightArr heightArr
     * @return int
     */
    private int catchRainTwo(int[] heightArr) {
        int n = heightArr.length;
        int[] leftMaxArr = new int[n];
        int[] rightMaxArr = new int[n];
        leftMaxArr[0] = heightArr[0];
        rightMaxArr[n - 1] = heightArr[n - 1];
        for (int i = 1; i < n; i++) {
            leftMaxArr[i] = Math.max(heightArr[i], leftMaxArr[i - 1]);
        }
        for (int i = n - 2; i >= 0; i--) {
            rightMaxArr[i] = Math.max(heightArr[i], rightMaxArr[i + 1]);
        }
        int amountOfWater = 0;
        for (int i = 1; i < heightArr.length - 1; i++) {
            amountOfWater += Math.min(leftMaxArr[i], rightMaxArr[i]) - heightArr[i];
        }
        return amountOfWater;
    }

    /**
     * 给你 n 个⾮负整数 a1，a2，...，an，其中每个数代表坐标中的⼀个点 (i, ai)。在坐标内画 n 条垂直
     * 线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的⽔。
     * <p>
     * 思路:
     * 位置i能够装的雨水为 Math.min(左边柱子最大高度,右边柱子的最大高度) - 位置i的高度
     * 即water[i] = Math.min(max(height[0...i]), max(height[i...end])) - height[i]
     */
    public static int catchRain(int[] heightArr) {
        int amountOfWater = 0;
        for (int i = 1; i < heightArr.length - 1; i++) {
            int height = heightArr[i];
            int leftMaxHeight = getLeftMaxHeight(i, heightArr);
            int rightMaxHeight = getRightMaxHeight(i, heightArr);
            int thresholdHeight = Math.min(leftMaxHeight, rightMaxHeight);
            amountOfWater += thresholdHeight - height;
        }
        return amountOfWater;
    }


    private static int getRightMaxHeight(int i, int[] heightArr) {
        int rightMax = heightArr[heightArr.length - 1];
        for (int j = i; j < heightArr.length - 1; j++) {
            if (heightArr[j] > rightMax) {
                rightMax = heightArr[j];
            }
        }
        return rightMax;
    }

    private static int getLeftMaxHeight(int i, int[] heightArr) {
        int leftMax = heightArr[0];
        for (int j = i; j >= 0; j--) {
            // if (heightArr[j] > leftMax) {
            //     leftMax = heightArr[j];
            // }
            leftMax = Math.max(leftMax, heightArr[j]);
        }
        return leftMax;
    }




}
