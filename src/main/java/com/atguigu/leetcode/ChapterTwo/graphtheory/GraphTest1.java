package com.atguigu.leetcode.ChapterTwo.graphtheory;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/5 20:54
 */
public class GraphTest1 {
    /**
     * 力扣第 797 题「所有可能路径」，函数签名如下：
     * 题目输入一幅有向无环图，这个图包含n个节点，标号为0, 1, 2,..., n - 1，请你计算所有从节点0到节点n - 1的路径。
     * <p>
     * 输入的这个graph其实就是「邻接表」表示的一幅图，graph[i]存储这节点i的所有邻居节点。
     * <p>
     * 比如输入graph = [[1,2],[3],[3],[]]，就代表下面这幅图：
     * 算法应该返回[[0,1,3],[0,2,3]]，即0到3的所有路径。
     * <p>
     * 以0为起点遍历图，同时记录遍历过的路径，当遍历到终点时将路径记录下来即可。
     */
    private final List<List<Integer>> res = new ArrayList<>();

    @Test
    public void tesallPathsSourceTargett1() {
        int[][] graph  = new int[][]{{1,2},{3},{3},{}};
        List<List<Integer>> lists = allPathsSourceTarget(graph);
        System.out.println(lists);
    }

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        LinkedList<Integer> path = new LinkedList<>();
        traverse(0, graph, path);
        return res;
    }

    /**
     * @param i     图的起始节点.
     * @param graph 图的邻接矩阵
     * @param path  路径链表.
     */
    private void traverse(int i, int[][] graph, LinkedList<Integer> path) {
        // 做选择, 该节点加入路径中
        path.addLast(i);  //[[1,2],[3],[3],[]]
        if (i == graph.length - 1) {
            res.add(new ArrayList<>(path));
            path.removeLast();
            return;
        }
        int[] currentNodeNeighbors = graph[i];
        for (int currentNodeNeighbor : currentNodeNeighbors) {
            traverse(currentNodeNeighbor, graph, path);
        }
        // 撤销选择 该节点从路径中移除
        path.removeLast();

    }
    // Graph graph;
    // boolean[] visited;
    //
    // /* 有向图含有环 的遍历框架 */
    // void traverse(Graph graph, int s) {
    //     if (visited[s]) return;
    //     // 经过节点 s
    //     visited[s] = true;
    //     for (TreeNode neighbor : graph.neighbors(s))
    //         traverse(neighbor);
    //     // 离开节点 s
    //     visited[s] = false;
    // }
    // ，当有向图含有环的时候才需要visited数组辅助，如果不含环，连visited数组都省了，基本就是多叉树的遍历。

}
