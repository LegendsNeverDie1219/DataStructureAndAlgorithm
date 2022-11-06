package com.atguigu.oj;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/6 20:49
 */
public class DijkstraTest {

    // @Test
    // public void testDijkstra() {
    //     int[][] times = {{2,1,1},{2,3,1},{3,4,1}};
    //     int n = 4;
    //     int k = 2;
    //     int i = networkDelayTime(times, n, k);
    //     System.out.println(i);
    // }

    /**
     * 743 网络延迟时间.
     * 给你一个列表times，表示信号经过 有向 边的传递时间。times[i] = (ui, vi, wi)，其中ui是源节点，vi是目标节点，
     * wi是一个信号从源节点传递到目标节点的时间。
     * 现在，从某个节点K发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回-1 。
     *
     * @param times times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
     * @param n     一共有多少个节点 n =4
     * @param k     从节点k 出发. k = 2
     * @return
     */
    int networkDelayTime(int[][] times, int n, int k) {
        //  1.利用times数组构造图 因为节点编号是从1开始的.所以初始化容量为 1+n,避免索引偏移.
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < n+1; i++) {
            graph[i] = new ArrayList<>();
        }
        // graph  index 表示的是源节点 -> List<Object> 中每一个对象 表示的是 源节点 对应的(邻接节点,以及权值)
        for (int[] edge : times) {
            int fromNodeIndex = edge[0];
            int toNodeIndex = edge[1];
            int weight = edge[2];
            graph[fromNodeIndex].add(new int[]{toNodeIndex, weight});
        }
        // 2.使用 dijkstra 算法
        int[] minDistanceArr = dijkstra(k, graph);
        int maxValue = 0;
        // 3. 获取数组中距离最大的那个值. 如果和 Integer.MAX_VALUE 相同 则 为-1. 否则就是有解
        for (int distance : minDistanceArr) {
            if (distance == Integer.MAX_VALUE) {
                return -1;
            }
            maxValue = Math.max(distance, maxValue);
        }
        return maxValue;

    }

    /**
     * 输入一幅图,和一个起点start,计算start到其他节点的最短距离.
     *
     * @param start 起点start
     * @param graph 一幅图.
     * @return 起点到其他节点的最短距离形成的数组.
     */
    int[] dijkstra(int start, List<int[]>[] graph) {
        int nodeSize = graph.length;
        //distanceTo[index]表示 : start节点 到 index 节点 的最短距离
        int[] distanceTo = new int[nodeSize];
        Arrays.fill(distanceTo, Integer.MAX_VALUE);
        distanceTo[0] = 0; //todo
        distanceTo[start] = 0;

        //  优先级队列，distFromStart 较小的排在前面
        PriorityQueue<Status> queue = new PriorityQueue<>(new Comparator<Status>() {
            @Override
            public int compare(Status status1, Status status2) {
                return status1.distanceFromStart - status2.distanceFromStart;
            }
        });
        // 从start出发.开始BFS
        queue.offer(new Status(start, 0));


        while (!queue.isEmpty()) {
            // todo 写到寻找相邻节点的for循环外面.
            Status status = queue.poll();
            int currentNodeId = status.nodeId;
            int distanceFromStart = status.distanceFromStart;

            //起点到当前节点的距离 大于已记录的  最小距离. 说明从起点 到达 当前节点 已经存在一个更好的方案.
            if (distanceFromStart > distanceTo[currentNodeId]) {
                continue;
            }


            // 将currnode 的相邻节点加入到队列中.
            for (int[] nextNodeArr : graph[currentNodeId]) {
                int nextNodeId = nextNodeArr[0];
                // todo start到 currentNodeId的最短距离 + currentNode到nextNode的距离.  与start到 nextNode的最短距离进行比较.
                int distanceToNextNode = distanceTo[currentNodeId] + nextNodeArr[1];

                if (distanceToNextNode < distanceTo[nextNodeId]) {
                    // 将符合条件的节点加入队列 并更新DP表.
                    queue.offer(new Status(nextNodeId, distanceToNextNode));
                    // start节点 到 nextNodeId 节点 的最短距离 为 distanceTo[nextNodeId]
                    distanceTo[nextNodeId] = distanceToNextNode;
                }
            }
        }

        return distanceTo;
    }

    private int weight(int currentNodeId, int nextNodeId) {
        return 0;
    }

    static class Status {
        // 记录当前节点的ID
        int nodeId;
        // 记录从start节点 到当前节点的距离.
        int distanceFromStart;

        public Status(int nodeId, int distanceFromStart) {
            this.nodeId = nodeId;
            this.distanceFromStart = distanceFromStart;
        }
    }
}
