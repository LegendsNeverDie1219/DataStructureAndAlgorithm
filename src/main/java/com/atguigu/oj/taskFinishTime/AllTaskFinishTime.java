package com.atguigu.oj.taskFinishTime;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/1 11:41
 */
public class AllTaskFinishTime {
    /**
     * 给定一个正数, 表示有0~n-1号任务
     * 给定一个长度为n的数组time, time[i] 表示i号任务做完的时间
     * 给定一个二维数组,matrix
     * matix[j] = [a,b] 表示a 任务完成依赖b任务的完成
     * 只要能并行的任务都可以并行,但是任何任务只有依赖的任务完成,才能开始
     * 返回一个长度为n的数组ans,表示每个任务完成的时间.
     * 输入可以保证没有循环依赖.
     */
    /**
     * @param n      一共有多少个任务 任务编号从0开始, 即有下标为[0,n-1]范围的任务.
     * @param time   每个任务执行的时间
     * @param matrix 依赖关系.
     * @return 每个任务完成的时间.
     */
    public static int[] finishTime(int n, int[] time, int[][] matrix) {
        // 刻画了每个编号的任务 被哪些任务所依赖.
        // index 为任务编号,  dependGraph.get(index) 为依赖该基础任务的 高级任务集合.
        List<List<Integer>> dependGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dependGraph.add(new ArrayList<>());
        }
        // 刻画了每个编号的任务 的依赖度/入度为多少. 入度为0的则表示不依赖其他
        // index为任务编号, dependDegree[index] 为该任务编号的入度/依赖度.
        int[] dependDegree = new int[n];
        for (int i = 0; i < matrix.length; i++) {
            int[] oneDependRelation = matrix[i];
            int dependTaskNo = oneDependRelation[0];
            int baseTaskNo = oneDependRelation[1];
            dependGraph.get(baseTaskNo).add(dependTaskNo);
            dependDegree[dependTaskNo]++;
        }

        Queue<Integer> taskExecuteQueue = new LinkedList<>();
        for (int taskNo = 0; taskNo < n; taskNo++) {
            if(dependDegree[taskNo] == 0) {
                taskExecuteQueue.offer(taskNo);
            }
        }
        int[] finishTime = new int[n];
        while (!taskExecuteQueue.isEmpty()) {
            Integer baseTaskNo = taskExecuteQueue.poll();
            finishTime[baseTaskNo] = finishTime[baseTaskNo] + time[baseTaskNo];

            List<Integer> dependTaskNos = dependGraph.get(baseTaskNo);

            for (Integer dependTaskNo : dependTaskNos) {
                // 依赖的任务执行 的先决条件 是完成所有的基础任务, 则需要得到依赖的基础任务的最大时间.
                // 把当前基础任务的执行时间广播给 依赖任务.
                finishTime[dependTaskNo] = Math.max(finishTime[dependTaskNo], finishTime[baseTaskNo]);

                dependDegree[dependTaskNo]--;

                if (dependDegree[dependTaskNo] ==0) {
                    taskExecuteQueue.offer(dependTaskNo);
                }
            }

        }
        return finishTime;
    }

    public static void main(String[] args) {
        int n = 6;
        int[] time = new int[]{5, 3, 4, 2, 6, 5};
        int[][] matrix = new int[5][2];
        matrix[0] = new int[]{1, 0};
        matrix[1] = new int[]{4, 2};
        matrix[2] = new int[]{3, 1};
        matrix[3] = new int[]{5, 4};
        matrix[4] = new int[]{3, 2};
        System.out.println(Arrays.toString(finishTime(n, time, matrix)));
    }
}
