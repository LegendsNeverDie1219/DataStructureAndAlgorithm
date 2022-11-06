package com.atguigu.oj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/17 21:28
 */
public class PerfectAnswerCollection {
    /**
     * 考生在线平台考试结束之后,可以查看自己每道题的结果
     * 假设共有questionsCount道题目,题目编号从1 到questionCount ,现在给出每个考生的答对题目的编号,
     * 格式如 1 3 表示答对 1~3题目,9 9 表示答对第9道题目.
     * <p>
     * 考生答对的题目是连续的.
     * 每个考生至少答对1道题目.
     * 请计算至少需要综合多少个考生的正确答案才能得到完美的答案.如果无法综合到完美的答案.则输出-1;
     * <p>
     * 第一行一个整数, 表示题目的总数量 questionCount
     * 第二行一个整数, 表示考生人数 peopleCount.
     * 解下来peopleCount行,每行两个整数 start end  1<=start<=end<=questionCount
     * <p>
     * 输出:
     * 一个整数.表示可以综合到完美答案的最少人数.如果无法综合到完美答案.则输出-1.
     * <p>
     * 10
     * 6
     * 1 3
     * 4 6
     * 1 6
     * 6 10
     * 5 8
     * 10 10
     * <p>
     * 输出样例
     * 2 至少需要两个考生.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int questionsCount = scanner.nextInt();
        int peopleCount = scanner.nextInt();
        int[][] correctRanges = new int[peopleCount][];
        for (int i = 0; i < peopleCount; i++) {
            correctRanges[i] = new int[2];
            correctRanges[i][0] = scanner.nextInt();
            correctRanges[i][1] = scanner.nextInt();
        }
        scanner.close();
        System.out.println(getMinPeople(questionsCount, peopleCount, correctRanges));
    }

    private static int getMinPeople(int questionsCount, int peopleCount, int[][] correctRanges) {
        // 思路: 采用贪心算法进行计算
        // 第一次时 找到所有 覆盖第一个道题目的解的集合 .之后求一个最优解(即end 最大的. end大,说明覆盖的题目数量多.)
        // 如果这个最优解存在,则下次就从最优解的end+1 开始, 找到覆盖 第end+1道题目 的所有解的集合. 之后再求一个最优解.
        // 直到end ==questionsCount 说明第questionsCount 也覆盖了.

        // 开始的时候一道题目也没有覆盖.
        int end = 0;
        int studentNumber = 0;
        while (end < questionsCount) {
            int currentCoverTitle = end + 1;
            Optional<int[]> bestInterval = Arrays.stream(correctRanges)
                    // 寻找可行解
                    .filter(interval -> currentCoverTitle >= interval[0] && currentCoverTitle <= interval[1])
                    // 寻找最优解
                    .max(new Comparator<int[]>() {
                        @Override
                        public int compare(int[] interval1, int[] interval2) {
                            // 升序排序 ,则执行max之后,会取到end最大的区间.
                            return Integer.compare(interval1[1], interval2[1]);
                        }
                    });
            // 说明 当前需要覆盖的题目 没有区间可以满足.
            if (!bestInterval.isPresent()) {
                return -1;
            }
            // 更新 已经覆盖的题目区间  的终点.
            end = bestInterval.get()[1];
            studentNumber++;
        }

        return studentNumber;

    }
}
