package com.atguigu.leetcode.ChapterThree;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/6/1 7:43
 */
public class IslandProblem {


    /**
     * 递归遍历二维矩阵的框架
     * 可以把二维矩阵中的每个位置看做是一个节点,  该节点有上下左右四个相邻节点, 则遍历递归遍历二维矩阵,就是遍历一个图
     */
    void traverseMatrix() {
        int[][] matrix = new int[3][3];
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                backTrackForMatrix(matrix, i, j, visited);
            }
        }
    }

    void backTrackForMatrix(int[][] matrix, int i, int j, boolean[][] visited) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        if (visited[i][j]) {
            return;
        }
        visited[i][j] = true;


        backTrackForMatrix(matrix, i - 1, j, visited);
        backTrackForMatrix(matrix, i + 1, j, visited);
        backTrackForMatrix(matrix, i, j - 1, visited);
        backTrackForMatrix(matrix, i, j + 1, visited);


        visited[i][j] = false;
    }

    /**
     * 计算岛屿的数量
     *
     * @param grid 二维矩阵
     * @return 岛屿的数量
     */
    int numIslands(char[][] grid) {
        int numberOfIsLand = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    // 说明该方格是一个岛屿,每发现一个岛屿,就将岛屿数量加1
                    numberOfIsLand++;
                    // 说明该方格是一个岛屿, 使用dfs把该位置相连的方格 组成的岛屿给淹没掉
                    // 目的: 在不使用visited数组的同时,又可以避免走回头路,
                    backTrackForNumIslands(grid, i, j);

                }
            }
        }
        return numberOfIsLand;
    }

    private void backTrackForNumIslands(char[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        // 该方格为洪水,返回
        if (grid[i][j] == '0') {
            return;
        }
        // 该方格设置为洪水.
        grid[i][j] = '0';
        backTrackForNumIslands(grid, i, j - 1);
        backTrackForNumIslands(grid, i, j + 1);
        backTrackForNumIslands(grid, i - 1, j);
        backTrackForNumIslands(grid, i + 1, j);
    }

    /**
     * 一题说二维矩阵四周可以认为也是被海水包围的，所以靠边的陆地也算作岛屿。
     * <p>
     * 力扣第 1254 题「统计封闭岛屿的数目」和上一题有两点不同：
     * <p>
     * 1、用0表示陆地，用1表示海水。
     * <p>
     * 2、让你计算「封闭岛屿」的数目。所谓「封闭岛屿」就是上下左右全部被1包围的0，也就是说靠边的陆地不算作「封闭岛屿」。
     *
     * @param grid
     * @return
     */
    public int closeIsLand(int[][] grid) {
        // 思路: 洪水填方法
        // 先把靠边的(上下左右) 岛屿都淹没掉,然后再进行统计
        int m = grid.length;
        int n = grid[0].length;

        for (int j = 0; j < n; j++) {
            // 最上面的岛屿淹没掉
            backTrackForCloseIsLand(grid, 0, j);
            // 最下面的岛屿淹没掉
            backTrackForCloseIsLand(grid, m - 1, j);
        }

        for (int i = 0; i < m; i++) {
            // 最左边的岛屿淹没掉
            backTrackForCloseIsLand(grid, i, 0);
            // 最右边的岛屿淹没掉
            backTrackForCloseIsLand(grid, i, n - 1);
        }

        // 上下左右都已经淹没了,开始进行统计
        int result = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    result++;
                    // 该方格是陆地, 则计数加一, 并且把该陆地方格相连的所有 陆地方格都用洪山淹没
                    backTrackForCloseIsLand(grid, i, j);
                }
            }
        }
        return result;
    }

    private void backTrackForCloseIsLand(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        // base case 遇到洪山,返回.避免走回头路.
        if (grid[i][j] == 1) {
            return;
        }

        // 该方格使用洪水填方
        grid[i][j] = 1;
        // 相邻的方格也使用 洪水填方
        backTrackForCloseIsLand(grid, i - 1, j);
        backTrackForCloseIsLand(grid, i + 1, j);
        backTrackForCloseIsLand(grid, i, j - 1);
        backTrackForCloseIsLand(grid, i, j + 1);
    }

    /**
     * 第 1020 题「飞地的数量」，这题不让你求封闭岛屿的数量，而是求封闭岛屿的面积总和。
     * 其实思路都是一样的，先把靠边的陆地淹掉，然后去数剩下的陆地数量就行了，注意第 1020 题中1代表陆地，0代表海水：
     */
    public int numEnclaves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 淹掉靠边的陆地
        for (int i = 0; i < m; i++) {
            backTrackForNumEnclaves(grid, i, 0);
            backTrackForNumEnclaves(grid, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            backTrackForNumEnclaves(grid, 0, j);
            backTrackForNumEnclaves(grid, m - 1, j);
        }

        // 数一数剩下的陆地
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 数一数剩下的方格,不用再淹没了.
                    res += 1;
                }
            }
        }

        return res;
    }

    private void backTrackForNumEnclaves(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        // base case 遇到洪山,返回.避免走回头路.
        if (grid[i][j] == 0) {
            return;
        }

        // 该方格使用洪水填方
        grid[i][j] = 0;
        // 相邻的方格也使用 洪水填方
        backTrackForNumEnclaves(grid, i - 1, j);
        backTrackForNumEnclaves(grid, i + 1, j);
        backTrackForNumEnclaves(grid, i, j - 1);
        backTrackForNumEnclaves(grid, i, j + 1);
    }

    /**
     * 这是力扣第 695 题「岛屿的最大面积」，0表示海水，1表示陆地，现在不让你计算岛屿的个数了，
     * 而是让你计算最大的那个岛屿的面积，函数签名如下：
     */
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int maxArea = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 把该方格已经相邻的陆路方格给淹没了, 并且返回淹没的岛屿面积
                    int area = backTrackAreaOfIsland(grid, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }

    private int backTrackAreaOfIsland(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return 0;
        }
        if (grid[i][j] == 0) {
            return 0;
        }

        grid[i][j] = 0;
        int upperNodeNumber = backTrackAreaOfIsland(grid, i - 1, j);
        int downNodeNumber = backTrackAreaOfIsland(grid, i + 1, j);
        int leftNodeNumber = backTrackAreaOfIsland(grid, i, j - 1);
        int rightNodeNumber = backTrackAreaOfIsland(grid, i, j + 1);
        return 1 + upperNodeNumber + downNodeNumber + leftNodeNumber + rightNodeNumber;
    }

    /**
     * 如果说前面的题目都是模板题，那么力扣第 1905 题「统计子岛屿」可能得动动脑子了：
     * 给你两个m x n的二进制矩阵grid1 和grid2，它们只包含0（表示水域）和 1（表示陆地）。
     * 一个 岛屿是由 四个方向（水平或者竖直）上相邻的1组成的区域。任何矩阵以外的区域都视为水域。
     * <p>
     * 如果 grid2的一个岛屿，被 grid1的一个岛屿完全 包含，
     * 也就是说 grid2中该岛屿的每一个格子都被 grid1中同一个岛屿完全包含，那么我们称 grid2中的这个岛屿为 子岛屿。
     * <p>
     * 请你返回 grid2中 子岛屿的 数目。
     */
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        // 思路: 子岛屿:即 岛屿b中的所有陆地方格  在岛屿a中也必须是陆地方格
        // 反过来 ,如果岛屿b中的所有陆地方格  其中有一个方格 在岛屿a中是海水方格, 则是有问题的.则淹没掉这些不合法的岛屿
        // 再对剩余的方格进行计数
        int m = grid1.length;
        int n = grid1[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1 && grid1[i][j] == 0) {
                    backTrackForCountSubIslands(grid2, i, j);
                }
            }
        }
        int subIslandCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    subIslandCount++;
                    backTrackForCountSubIslands(grid2, i, j);
                }
            }
        }
        return subIslandCount;
    }

    private void backTrackForCountSubIslands(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        // base case
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        // base case 遇到洪山,返回.避免走回头路.
        if (grid[i][j] == 0) {
            return;
        }

        // 该方格使用洪水填方
        grid[i][j] = 0;
        // 相邻的方格也使用 洪水填方
        backTrackForCountSubIslands(grid, i - 1, j);
        backTrackForCountSubIslands(grid, i + 1, j);
        backTrackForCountSubIslands(grid, i, j - 1);
        backTrackForCountSubIslands(grid, i, j + 1);
    }

    /**
     * 这是本文的最后一道岛屿题目，作为压轴题，当然是最有意思的。
     * <p>
     * 力扣第 694 题「不同的岛屿数量」，题目还是输入一个二维矩阵，0表示海水，1表示陆地，
     * 这次让你计算 不同的 (distinct) 岛屿数量，函数签名如下
     */
    public int numDistinctIslands(int[][] grid) {
        /**
         * 思路:
         *  要想刻画岛屿的二维形状并且去重, 则需要将岛屿转化为一维的字符串,/即序列化,然后使用hashset进行去重.
         *  岛屿的形状其实可以用递归遍历时 指针走过的方格路径表示.
         *  比如 下, 右 ,上 撤销上, 撤销右, 撤销下. 然后每次发现一个岛屿 就把这个走过的方格路径记录下来.
         *  最后进行一个去重
         */
        int m = grid.length;
        int n = grid[0].length;
        Set<String> pathSet = new HashSet<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder sb = new StringBuilder();
                    int direction = -1;
                    // 每当发现一个岛屿, 则把岛屿淹没,并且记录淹没时的路径.
                    backTrack4NumDistinctIslands(grid, i, j, direction, sb);
                    pathSet.add(sb.toString());
                }
            }
        }

        return pathSet.size();
    }

    private void backTrack4NumDistinctIslands(int[][] grid, int i, int j, int direction, StringBuilder sb) {
        int m = grid.length;
        int n = grid[0].length;

        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        if (grid[i][j] == 0) {
            return;
        }
        // 记录淹没的路径轨迹 即通过 往那个方向走 才走到了grid[i][j]位置.
        grid[i][j] = 0;
        // 做选择
        sb.append(direction).append(',');
        backTrack4NumDistinctIslands(grid, i - 1, j, 1, sb); // 向上走
        backTrack4NumDistinctIslands(grid, i + 1, j, 2, sb);// 向下走
        backTrack4NumDistinctIslands(grid, i, j - 1, 3, sb);// 向左走
        backTrack4NumDistinctIslands(grid, i, j + 1, 4, sb);// 向右走

        // 撤销选择 ,后序位置
        sb.append(-direction).append(',');
    }


}
