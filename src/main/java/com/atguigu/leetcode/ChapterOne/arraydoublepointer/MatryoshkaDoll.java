package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 俄罗斯套娃
 *
 * @author Administrator
 * @date 2022/3/23 20:20
 */
public class MatryoshkaDoll {
    /**
     * 给你⼀个⼆维整数数组 envelopes，其中 envelopes[i] = [wi, hi]，表示第 i 个信封的宽度和⾼度。
     * 当另⼀个信封的宽度和⾼度都⽐这个信封⼤的时候，这个信封就可以放进另⼀个信封⾥，如同俄罗斯套娃⼀
     * 样。
     * 请计算 最多能有多少个信封能组成⼀组“俄罗斯套娃”信封（即可以把⼀个信封放到另⼀个信封⾥⾯）。
     * 注意：不允许旋转信封。
     * 示例 1：
     * 输⼊：envelopes = [[5,4],[6,4],[6,7],[2,3]]
     * 输出：3
     * 解释：最多信封的个数为 3, 组合为：[2,3] => [5,4] => [6,7]。
     */

    /**
     * 分析:
     * 首先将这个二维数组先按照宽度升序排序, 如果宽度相同,,则按照高度进行降序排序
     * 接着获取排序后二维数组的 高度数据,组成一个新的数组heightArr
     * 则最多信封个数问题 可以转化为 求heightArr数组的最长子序列的问题
     * <p>
     * 最长递增子序列问题,可以采用两种方式
     * 方式一: 动态规划, 时间复杂度为O(n^2) 即需要定义一个数组 D
     * 其中D[i] 记录的是 以数字nums[i]结尾的 最长递增子序列的 长度
     * 例如:nums为[1,4,3,4,2] 则按照指定规则计算得到的 数组D为[1,2,2,3,2]
     * 其中数组D中的最大值 就是最长递增子序列的长度
     * <p>
     * <p>
     * 方式二: 二分查找(时间复杂度为 O(NLogN))
     * 其中需要按照纸牌游戏模型进行推演
     * 情景: 给你一排无序的扑克牌,按照从左到右的顺序每次取出一张, 然后找合适的牌堆 去放这一张排.
     * 其中要求 点数小的牌要放置到点数大的牌上面, 如果某一张牌点数很大,则新起 一堆, 放这一张排.
     * 如果一张牌有多个堆可以选择, 则放置到最左边的牌堆.
     * 则 此时牌堆的个数就是最长递增子序列的长度
     * 并且每一堆牌的 顶部牌的集合是有序的.
     */
    @Test
    public void tesDynamicProgress2SolveMatryoshkaDoll() {
        int[][] envelopes = new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}};

        int i = dynamicProgress2SolveMatryoshkaDoll(envelopes);
        System.out.println(i);
    }

    public int dynamicProgress2SolveMatryoshkaDoll(int[][] envelopes) {
        Arrays.sort(envelopes, (elementOne, elementTwo) -> {
            if (elementOne[0] == elementTwo[0]) {
                return elementTwo[1] - elementOne[1];
            }
            // 按照宽度进行升序排序
            return elementOne[0] - elementTwo[0];
        });

        int[] envelopeHeights = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            int height = envelopes[i][1];
            envelopeHeights[i] = height;
        }

        return getLengthOfLis(envelopeHeights);
    }

    private int getLengthOfLis(int[] envelopeHeights) {
        // 定义一个数组D ,其中D[i] 存放的是以 数字envelopeHeights[i]结尾的 最长递增子序列 的长度
        int[] lengthOfLongestIncreaseSubsequenceArr = new int[envelopeHeights.length];
        Arrays.fill(lengthOfLongestIncreaseSubsequenceArr, 1);
        // [3, 4, 7, 4]
        // 计算D[1]
        for (int num = 1; num < envelopeHeights.length; num++) {
            for (int j = 0; j < num; j++) {
                if (envelopeHeights[num] > envelopeHeights[j]) {
                    lengthOfLongestIncreaseSubsequenceArr[num] = lengthOfLongestIncreaseSubsequenceArr[j] + 1;
                }
            }
        }
        int maxValue =
                Arrays.stream(lengthOfLongestIncreaseSubsequenceArr).max().orElse(lengthOfLongestIncreaseSubsequenceArr[0]);
        System.out.println("maxValue: " + maxValue);
        int maxValueTwo = lengthOfLongestIncreaseSubsequenceArr[0];
        for (int i = 1; i < lengthOfLongestIncreaseSubsequenceArr.length; i++) {
            if (lengthOfLongestIncreaseSubsequenceArr[i] > maxValueTwo) {
                maxValueTwo = lengthOfLongestIncreaseSubsequenceArr[i];
            }
        }
        return maxValueTwo;
    }

    @Test
    public void testBinarySearch2SolveMatryoshkaDoll() {
        int[][] envelopes = new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}};
        int result = binarySearch2SolveMatryoshkaDoll(envelopes);
        System.out.println(result);

    }

    public int binarySearch2SolveMatryoshkaDoll(int[][] envelopes) {
        Arrays.sort(envelopes, (elementOne, elementTwo) -> {
            if (elementOne[0] == elementTwo[0]) {
                return elementTwo[1] - elementOne[1];
            }
            // 按照宽度进行升序排序
            return elementOne[0] - elementTwo[0];
        });

        int[] envelopeHeights = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            int height = envelopes[i][1];
            envelopeHeights[i] = height;
        }
        return getLengthOfLis_2(envelopeHeights);
    }

    private int getLengthOfLis_2(int[] envelopeHeights) {
        int cardHeapNumber = 0;
        // 数组初始化最大容量,以防止越界(index为哪一堆牌, value 为第这一堆牌的牌顶元素)
        int[] cardHeapTop = new int[envelopeHeights.length];

        for (int i = 0; i < envelopeHeights.length; i++) {
            // 新取出一张牌,获取要放置的位置,即哪一堆中(二分查找获取左边界)
            int card = envelopeHeights[i];
            int left = 0;
            int right = cardHeapNumber;
            // 搜索范围是 [left,right)
            while (left < right) {
                int middleHeap = left + (right - left) / 2;
                if(cardHeapTop[middleHeap] < card) {
                    // [middleHeap+1,right)
                    left = middleHeap +1;
                } else if(cardHeapTop[middleHeap] >card) {
                    // [left,middeleHeap)
                    right = middleHeap;
                } else {
                    // 向左收缩,进而锁定左边界.
                    right = middleHeap;
                }
            }

            // 没有找到合适的牌堆,则新建一个牌堆.
            if (left  == cardHeapNumber) {
                cardHeapNumber++;
            }
            // 把这一张牌,放到牌堆顶部.
            cardHeapTop[left] = card;
        }
        // 牌堆数就是 LIS的长度.
        return cardHeapNumber;

    }

}
