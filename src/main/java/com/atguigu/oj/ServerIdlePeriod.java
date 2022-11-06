package com.atguigu.oj;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/22 13:23
 */
public class ServerIdlePeriod {
    /**
     * 现在有serverNum台服务器 和taskNum 项任务, 并将任务按时段分配给各服务器,请你统计恰好有且只有一台服务器空闲的时段
     * 最后请输出排序后的空闲时段.
     * 统计范围的最大时刻为 当前用力的设计的最大时刻.
     * 须和并连续的时段 如[1,2] [2,3] 合并为[1,3]
     * 按照时段的开始时刻的大小升序排序.
     * 若无满足要求的时段. 输出区间 -1,-1
     * <p>
     * 输入:
     * 第一行一个整数 serverNum,表示服务器个数, 取值范围[1,10^4]
     * 第二行一个整数taskNum,表示任务个数.取值范围[1,10^4]
     * 接下来taskNum行,每行3个整数 startTime endTime serverId 分别表示一个任务的开始时刻,结束时刻.服务器编号.
     * 取值范围 0 < startTime< endTime< 10^9 1 <=serverId<-serverNum
     * <p>
     * 输出:
     * 每行两个帧数,表示一个空闲时段  格式为: startTime endTime
     * <p>
     * 样例
     * 输入样例1
     * 3
     * 5
     * 1 2 1
     * 1 3 1
     * 5 6 1
     * 2 3 2
     * 5 6 3
     * <p>
     * 输出样例1
     * 2 3
     * 5 6
     * <p>
     * <p>
     * 输入样例2
     * 4
     * 6
     * 1 2 1
     * 1 2 2
     * 1 2 4
     * 2 3 1
     * 2 3 2
     * 2 3 3
     * <p>
     * 输出样例2
     * 1 3
     * <p>
     * <p>
     * 输入样例3
     * 2
     * 3
     * 1 3 1
     * 1 2 2
     * 2 3 2
     * 输出样例3
     * -1 -1
     */
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int serverNum = cin.nextInt();
        int taskNum = cin.nextInt();
        Task[] tasks = new Task[taskNum];
        for (int i = 0; i < taskNum; i++) {
            int startTime = cin.nextInt();
            int endTime = cin.nextInt();
            int serverId = cin.nextInt();
            tasks[i] = new Task(startTime, endTime, serverId);
        }
        cin.close();
        List<int[]> oneFreeTimes = getOneFreeTime(serverNum, tasks);
        oneFreeTimes.forEach(time -> System.out.println(time[0] + " " + time[1]));

    }
    // todo hds
    private static List<int[]> getOneFreeTime(int serverNum, Task[] tasks) {
        Set<Integer> intervalSet = new HashSet<>();
        for (Task task : tasks) {
            intervalSet.add(task.startTime);
            intervalSet.add(task.endTime);
        }
        Integer[] timeInterValArr = intervalSet.toArray(new Integer[0]);
        List<Integer> filterTimeInterval = new ArrayList<>();
        // 指针i 最后一次指向倒数第2个元素.
        for (int i = 0; i < intervalSet.size() - 1; i++) {
            //  intervalSet[i]~intervalSet[i+1] 统计每个时间段区间 有多少台服务器在运行.
            Set<Integer> runServerNos = new HashSet<>();
            for (Task task : tasks) {
                if (timeInterValArr[i] >= task.startTime && timeInterValArr[i + 1] <= task.endTime) {
                    runServerNos.add(task.serverId);
                }
            }
            // 此时间段内只有一台服务器空闲
            if (runServerNos.size() == serverNum - 1) {
                filterTimeInterval.add(timeInterValArr[i]);
                filterTimeInterval.add(timeInterValArr[i + 1]);
            }
        }
        List<int[]> resultList = new ArrayList<>();
        if (filterTimeInterval.isEmpty()) {
            resultList.add(new int[]{-1, -1});
            return resultList;
        }
        Integer startTime = filterTimeInterval.get(0);
        Integer endTime = filterTimeInterval.get(1);
        // 合并区间 从第3个数字开始.(每次循环 指针i都会移动 2步,保证 对比的时候 是拿上一个区间的end 和下一个区间的start进行比较.)
        for (int i = 2; i < filterTimeInterval.size() - 1; i++) {
            if (!endTime.equals(filterTimeInterval.get(i))) {
                // 把上一个区间的结果 存入集合. 并更新 startTime,endTime
                resultList.add(new int[]{startTime, endTime});
                startTime = filterTimeInterval.get(i);
                //指针移动1步.
                i++;
                endTime = filterTimeInterval.get(i);
            } else {
                i++;
                endTime = filterTimeInterval.get(i);
            }
        }
        resultList.add(new int[]{startTime, endTime});
        return resultList;
    }

    private static class Task {
        int startTime;
        int endTime;
        int serverId;

        public Task(int startTime, int endTime, int serverId) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.serverId = serverId;
        }
    }
}
