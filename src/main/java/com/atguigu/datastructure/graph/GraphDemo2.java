package com.atguigu.datastructure.graph;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/13 15:00
 */
public class GraphDemo2 {

    public static void main(String[] args) {
        // 1. 初始化图的信息,顶点,边,边的数量
        Graph graph = new Graph();
        initGraph(graph);
        //printInitInfo(graph);
        // 2.深度优先搜索 遍历
        depthFirstSearch(graph);
        System.out.println();
        // 3.广度优先搜索 遍历
        Graph graph2 = new Graph();
        initGraph(graph2);
        broadFirstSearch(graph2);
    }

    private static void broadFirstSearch(Graph graph2) {
        String[] vertexArray = graph2.getVertexArray();
        int[][] edgeArray = graph2.getEdgeArray();
        boolean[] isVisited = graph2.getIsVisited();
        for (int index = 0; index < vertexArray.length; index++) {
            if (!isVisited[index]) {
                bfs(index, isVisited, vertexArray, edgeArray);
            }
        }
    }

    private static void bfs(int index, boolean[] isVisited, String[] vertexArray, int[][] edgeArray) {
        // 1)访问初始节点v ,并标记为已经访问
        System.out.print(vertexArray[index] + "--->>");
        isVisited[index] = true;

        // 2)结点 v 入队列
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(index);

        // 3)当队列非空时,继续执行,否则,退出
        while (!queue.isEmpty()) {
            // 4)出队列,取得队列头部节点u
            Integer headNodeU = queue.removeFirst();
            // 5) 查找结点u的第一个邻接节点w
            int neighborNodeW = findFirstNeighborNode(edgeArray, headNodeU);
            // 6) 如果w不存在,则转到第3步, 否则循环执行以下三个步骤
            while (neighborNodeW != -1) {
                // 6.1) 如果结点w没有被访问过,则访问并标记该结点已经被访问
                // 6.2) 入队列
                // 6.3) 更新节点u的邻接节点  为w的下一个.转到步骤6
                if (!isVisited[neighborNodeW]) {
                    System.out.print(vertexArray[neighborNodeW] + "--->");
                    isVisited[neighborNodeW] = true;
                    queue.addLast(neighborNodeW);
                }
                neighborNodeW = updateNeighBorNodeIndex(headNodeU, neighborNodeW, edgeArray);
            }
        }
    }

    // 1.访问初始结点v，并标记结点v为已访问。
    // 2.查找结点v的第一个邻接结点w。
    // 3.若w存在，则继续执行4，如果w不存在，则回到第1步，将从v的下一个结点继续。
    // 4.若w未被访问，对w进行深度优先遍历递归（即把w当做另一个v，然后进行步骤123）。
    // 5.查找结点v的w邻接结点的下一个邻接结点，转到步骤3
    private static void dfs(int index, boolean[] isVisited, String[] vertexArray, int[][] edgeArray) {
        System.out.print(vertexArray[index] + " -> ");
        isVisited[index] = true;

        int neighborNodeIndexW = findFirstNeighborNode(edgeArray, index);

        while (neighborNodeIndexW != -1) {
            //节点v的邻接节点neighborNodeIndex 存在并且没有被访问过, 则进行深度优先遍历
            if (!isVisited[neighborNodeIndexW]) {
                dfs(neighborNodeIndexW, isVisited, vertexArray, edgeArray);
            }
            // 更新节点index 的邻接节点为neighborNodeIndex的下一个
            neighborNodeIndexW = updateNeighBorNodeIndex(index, neighborNodeIndexW, edgeArray);
        }

    }

    private static void printInitInfo(Graph graph) {
        System.out.println(Arrays.toString(graph.vertexArray));
        for (int i = 0; i < graph.edgeArray.length; i++) {
            System.out.println(Arrays.toString(graph.edgeArray[i]));
        }
        System.out.println(Arrays.toString(graph.isVisited));
        System.out.println(graph.validEdgeNumber);
    }

    private static void initGraph(Graph graph) {

        // graph.vertexArray = new String[]{"A", "B", "C", "D", "E"};
        graph.vertexArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};

        int vertexNum = graph.vertexArray.length;
        graph.edgeArray = new int[vertexNum][vertexNum];

        // insertEdgeInfo(0, 1, 1, graph);
        // insertEdgeInfo(0, 2, 1, graph);
        // insertEdgeInfo(1, 2, 1, graph);
        // insertEdgeInfo(1, 3, 1, graph);
        // insertEdgeInfo(1, 4, 1, graph);

        insertEdgeInfo(0, 1, 1, graph);
        insertEdgeInfo(0, 2, 1, graph);
        insertEdgeInfo(1, 3, 1, graph);
        insertEdgeInfo(1, 4, 1, graph);
        insertEdgeInfo(3, 7, 1, graph);
        insertEdgeInfo(4, 7, 1, graph);
        insertEdgeInfo(2, 5, 1, graph);
        insertEdgeInfo(2, 6, 1, graph);
        insertEdgeInfo(5, 6, 1, graph);

        graph.setIsVisited(new boolean[vertexNum]);
    }


    /**
     * insertEdgeInfo
     *
     * @param startVertexIndex 起始点的下标
     * @param endVertexIndex   终止顶点的下标
     * @param weight           权值
     * @param graph            graph
     */
    private static void insertEdgeInfo(int startVertexIndex, int endVertexIndex, int weight, Graph graph) {
        // 无向图
        int[][] edgeArray = graph.getEdgeArray();
        edgeArray[startVertexIndex][endVertexIndex] = weight;
        edgeArray[endVertexIndex][startVertexIndex] = weight;
        int validEdgeNumber = graph.getValidEdgeNumber();
        graph.setValidEdgeNumber(++validEdgeNumber);
    }

    // 深度优先搜索算法
    public static void depthFirstSearch(Graph graph) {
        String[] vertexArray = graph.getVertexArray();
        boolean[] isVisited = graph.getIsVisited();
        int[][] edgeArray = graph.getEdgeArray();


        for (int i = 0; i < vertexArray.length; i++) {
            if (!isVisited[i]) {
                dfs(i, isVisited, vertexArray, edgeArray);
            }
        }
    }


    private static int updateNeighBorNodeIndex(int index, int neighborNodeIndex, int[][] edgeArray) {
        for (int j = neighborNodeIndex + 1; j < edgeArray[index].length; j++) {
            if (edgeArray[index][j] != 0) {
                return j;
            }
        }
        return -1;
    }

    private static int findFirstNeighborNode(int[][] edgeArray, int index) {
        for (int j = 0; j < edgeArray[index].length; j++) {
            if (edgeArray[index][j] != 0) {
                return j;
            }
        }
        return -1;
    }

    @Setter
    @Getter
    private static class Graph {
        // 图是一种数据,在图中的每个结点可以有一个或多个相邻结点  结点又可以成为是顶点
        // 图的常用概念 : 顶点(vertex) 边(edge) 路径, 无向图, 有向图, 带权图
        // 图的两种表示形式, 二维数组表示(邻接矩阵) 一维数组加链表(邻接表)
        // 如果有n个顶点,邻接矩阵会为每个顶点都分配n个边的空间, 其中有很多边都不是真实存在,因此会有一定的空间浪费
        //  邻接表则不同,它只会记录真实存在的边.由数组和链表构成,因此不会造成空间的浪费

        // 顶点信息数组
        private String[] vertexArray;
        // 边信息数组(二维矩阵)
        private int[][] edgeArray;
        // 有效边的条数
        private int validEdgeNumber;
        // 若干顶点是否被访问过
        private boolean[] isVisited;
    }
}
