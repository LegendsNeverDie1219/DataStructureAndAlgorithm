package com.atguigu.Algorithm.prim;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/15 19:33
 */
public class PrimAlgorithm2 {
    public static void main(String[] args) {
        char[] vertexArr = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int vertexNumber = vertexArr.length;
        int[][] neighborMatrixArr = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},};
        MiniConnectGraph miniConnectGraph = new MiniConnectGraph();

        MiniGenerateTree miniGenerateTree = new MiniGenerateTree();
        miniGenerateTree.createMiniGenerateTree(miniConnectGraph, vertexNumber, vertexArr, neighborMatrixArr);

        int startVertex = 0;
        miniGenerateTree.primAlgorithm(miniConnectGraph, startVertex);


    }

    private static class MiniGenerateTree {

        public void createMiniGenerateTree(MiniConnectGraph miniConnectGraph, int vertexNumber, char[] vertexArr,
                                           int[][] neighborMatrixArr) {
            // 直接赋值的方式
            miniConnectGraph.setVertexNumber(vertexNumber);
            // miniConnectGraph.setVertexArr(vertexArr);
            // miniConnectGraph.setNeighborMatrixArr(neighborMatrixArr);
            // 或者可以使用拷贝的方式
            miniConnectGraph.vertexArr = new char[vertexNumber];
            miniConnectGraph.neighborMatrixArr = new int[vertexNumber][vertexNumber];
            for (int i = 0; i < vertexNumber; i++) {
                miniConnectGraph.vertexArr[i] = vertexArr[i];
                for (int j = 0; j < vertexNumber; j++) {
                    miniConnectGraph.neighborMatrixArr[i][j] = neighborMatrixArr[i][j];
                }
            }
            System.out.println("hds");
        }

        public void primAlgorithm(MiniConnectGraph miniConnectGraph, int startVertexIndex) {
            int vertexNumber = miniConnectGraph.vertexNumber;
            int[][] neighborMatrixArr = miniConnectGraph.neighborMatrixArr;
            char[] vertexArr = miniConnectGraph.vertexArr;
            // 定义一个数组,用来记录顶点是否被访问过
            boolean[] isVisited = new boolean[vertexNumber];
            // 设置出发顶点已经被访问
            isVisited[startVertexIndex] = true;


            for (int edgeNumber = 1; edgeNumber <= vertexNumber - 1; edgeNumber++) {
                int miniWeight = 10000;
                int visitedVertex = 0;
                int notVisitedVertex = 0;
                // 遍历邻接矩阵,寻找与已经访问过的若干顶点 visitedVertexList 距离最短的顶点,, 因为顶点个数为vertexNumber, 所以要寻找vertexNumber-1条边
                for (int i = 0; i < neighborMatrixArr.length; i++) {
                    for (int j = 0; j < neighborMatrixArr[i].length; j++) {
                        if (isVisited[i] && !isVisited[j] && neighborMatrixArr[i][j] < miniWeight) {
                            // 更新最短边的权值
                            miniWeight = neighborMatrixArr[i][j];
                            // 记录距离最短的边 的两个顶点, 一个是已经访问过的,一个尚未访问的.
                            visitedVertex = i;
                            notVisitedVertex = j;
                        }
                    }
                }
                System.out.println("与已经访问过的若干顶点 距离最短的顶点为: " + vertexArr[visitedVertex] + " -> " + vertexArr[notVisitedVertex] + " 权值为: " + miniWeight);
                // 设置j点已经被访问过
                isVisited[notVisitedVertex] = true;
                // // 重置miniWeight,visitedVertex,notVisitedVertex
                // int miniWeight = 10000;
                // int visitedVertex = 0;
                // int notVisitedVertex = 0;
            }

        }
    }

    @Setter
    @Getter
    private static class MiniConnectGraph {
        private int vertexNumber;
        private char[] vertexArr;
        private int[][] neighborMatrixArr;
    }
}
