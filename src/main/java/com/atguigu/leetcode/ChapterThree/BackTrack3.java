package com.atguigu.leetcode.ChapterThree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/27 7:20
 */
public class BackTrack3 {
    /**
     * 解决数独. 37
     * 1、数字 1-9 在每⼀⾏只能出现⼀次。
     * 2、数字 1-9 在每⼀列只能出现⼀次。
     * 3、数字 1-9 在每⼀个以粗实线分隔的 3x3 宫内只能出现⼀次。
     * 数独部分空格内已填⼊了数字，空⽩格⽤ '.' 表示。
     * <p>
     * 输⼊：board = [["5","3",".",".","7",".",".",".","."],
     * ["6",".",".","1","9","5",".",".","."],
     * [".","9","8",".",".",".",".","6","."],
     * ["8",".",".",".","6",".",".",".","3"],
     * ["4",".",".","8",".","3",".",".","1"],
     * ["7",".",".",".","2",".",".",".","6"],
     * [".","6",".",".",".",".","2","8","."],
     * [".",".",".","4","1","9",".",".","5"],
     * [".",".",".",".","8",".",".","7","9"]]
     * 输出：[["5","3","4","6","7","8","9","1","2"],
     * ["6","7","2","1","9","5","3","4","8"],
     * ["1","9","8","3","4","2","5","6","7"],
     * ["8","5","9","7","6","1","4","2","3"],
     * ["4","2","6","8","5","3","7","9","1"],
     * ["7","1","3","9","2","4","8","5","6"],
     * ["9","6","1","5","3","7","2","8","4"],
     * ["2","8","7","4","1","9","6","3","5"],
     * 在线⽹站 labuladong的刷题三件套
     * 292 / 485
     * ["3","4","5","2","8","6","1","7","9"]]
     * 解释：输⼊的数独如上图所示，唯⼀有效的解决⽅案如下所示：
     */
    public static void main(String[] args) {
        Integer[] integerArr = {1, 2, 3, 4};
        List<Integer> integers = Arrays.asList(integerArr);
        int[] intArr = {1, 2, 3, 4};
        List<int[]> ints = Arrays.asList(intArr);
    }

    @Test
    public void testSolveSudoku() {
        char[][] board = new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

        List<char[][]> resultList = solveSudoku(board);
        System.out.println("====results ");
        StringBuilder sb = new StringBuilder();
        resultList.forEach(item -> {
            System.out.println("第一个正确结果");
            printResult(sb, item);

        });
    }

    private void printResult(StringBuilder sb, char[][] item) {
        for (char[] chars : item) {
            oneRowData(sb, chars);
            System.out.println(sb);
            sb.delete(0, sb.length());
        }
    }

    private void oneRowData(StringBuilder sb, char[] chars) {
        sb.append("{");
        for (char ch : chars) {
            sb.append(ch);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}").append(System.lineSeparator());
    }

    public List<char[][]> solveSudoku(char[][] board) {
        List<char[][]> resList = new ArrayList<>();
        // rorChoice, colChoice 决定了决策树的宽度, 交换顺序之后会产生不同的结果, 所以是排列.
        // board 决定了决策树的深度,以及记录路径结果.

        // 选择: 第i行第j列的单元格 要放置什么数字?(1-9)
        // 路径: 第i行 第j-1 列 之前的众多单元格已经做出了选择, 记录在board中.
        // 结果集合.
        backTrackForSudoku(0, 0, board, resList);
        System.out.println("====board ");
        StringBuilder sb = new StringBuilder();
        printResult(sb, board);
        return resList;
    }

    private void backTrackForSudoku(int i, int j, char[][] board,
                                    List<char[][]> resList) {
        int rowNumber = 9;
        int colNumber = 9;
        // base case 如果本行所有的列都已经选择完毕, 则进入下一行.
        if (j == colNumber) {
            // 换一行继续进行选择
            backTrackForSudoku(i + 1, 0, board, resList);
            return;
        }

        // base case 如果行下标为 9 ,则说明前9行都已经选择好了,已经得到了一个正确结果.
        if (i == rowNumber) {
            char[][] newBoard = getNewBoard(board);
            resList.add(newBoard);
            return;
        }

        // base case 如果该位置是预制的数字,则不用我们选择,进入下一个单元格
        // todo 其中,这个判断要放到上面两个base case 的后面,不然会存在数组越界问题.
        if (board[i][j] != '.') {
            backTrackForSudoku(i, j + 1, board, resList);
            return;
        }

        // 选择的遍历.
        for (char number = '1'; number <= '9'; number++) {

            // 如果遇到不合法的数字,就跳过.
            if (isInValid(board, i, j, number)) {
                continue;
            }


            // 做选择
            board[i][j] = number;
            // 进入决策树下一层 (即放置下一个单元格.)
            backTrackForSudoku(i, j + 1, board, resList);
            // 取消选择
            board[i][j] = '.';
        }
    }

    private char[][] getNewBoard(char[][] board) {
        char[][] newBoard = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }

    private boolean isInValid(char[][] board, int i, int j, char number) {
        // 行是否重复
        for (int colIndex = 0; colIndex < 9; colIndex++) {
            if (board[i][colIndex] == number) {
                return true;
            }
        }

        // 列是否重复
        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            if (board[rowIndex][j] == number) {
                return true;
            }
        }

        // 小九宫格是否重复
        // 第一个九宫格[0,2] [0,2]
        for (int index = 0; index < 9; index++) {
            // i/3*3  以及 j/3*3 是将i,j 都拉回到自己所在的小就宫格的第一个位置.
            // index/3 以及 index% 3 则是对应的索引偏移量.
            if (board[i / 3 * 3 + index / 3][j / 3 * 3 + index % 3] == number) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testNQueen() {
        int n = 8;
        List<char[][]> resultList = nQueen(n);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < resultList.size(); index++) {
            char[][] board = resultList.get(index);
            System.out.println("第" + (index + 1) + "个正确结果");
            printResult(sb, board);
        }
    }

    /**
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。现在输⼊⼀
     * 个整数 n，请你返回所有不同的 n 皇后问题 的解决⽅案。
     * 每⼀种解法包含⼀个不同的 n 皇后问题 的棋⼦放置⽅案，该⽅案中 'Q' 和 '.' 分别代表了皇后和空位。
     */
    private List<char[][]> nQueen(int n) {
        // 选择 : 第start(start 从0开始)行的  哪一个位置要摆放皇后 (0~n-1 列中,选择一个合法的列)
        // 路径: 前start-1 行都已经摆放好了皇后. 皇后的摆放位置 都记录在board中.
        // base case : 触底条件 .即开始摆放第 n 行的皇后. (触底条件  + 正确条件 结果的更新, 异常条件的返回/跳过)
        int start = 0;
        char[][] board = new char[n][n];
        for (int i = 0; i < board.length; i++) {
            char[] oneRowData = new char[n];
            Arrays.fill(oneRowData, '.');
            board[i] = oneRowData;
        }
        List<char[][]> res = new ArrayList<>();
        backTrackNQueen(start, board, res);
        return res;
    }

    private void backTrackNQueen(int start, char[][] board, List<char[][]> res) {
        // base case  即是触底条件, 也是正确条件, 需要更新结果.
        if (start == board.length) {
            char[][] oneCorrectRes = getOneCorrectRes(board);
            res.add(oneCorrectRes);
            return;
        }

        // 第start行 要在哪一列摆放皇后.
        for (int index = 0; index < board[start].length; index++) {
            if (!isValidPosition(board, start, index)) {
                continue;
            }

            // 做选择
            board[start][index] = 'Q';
            // 进入下一层决策树
            backTrackNQueen(start + 1, board, res);
            // 撤销选择
            board[start][index] = '.';

        }
    }

    private char[][] getOneCorrectRes(char[][] board) {
        int columnLenth = board[0].length;
        char[][] oneCorrectRes = new char[board.length][columnLenth];
        for (int i = 0; i < oneCorrectRes.length; i++) {
            char[] oneRowData = new char[columnLenth];
            System.arraycopy(board[i], 0, oneRowData, 0, columnLenth);
            oneCorrectRes[i] = oneRowData;

            //System.arraycopy(board[i], 0, oneCorrectRes[i], 0, columnLenth);
        }
        return oneCorrectRes;
    }

    private boolean isValidPosition(char[][] board, int i, int j) {

        // 检查 上方位置方向,左上方位置的方向, 右上方位置的方向 是否存在 Queen
        for (int rowIndex = i - 1; rowIndex >= 0; rowIndex--) {
            if (board[rowIndex][j] == 'Q') {
                return false;
            }
        }

        for (int rowIndex = i - 1, colIndex = j - 1; rowIndex >= 0 && colIndex >= 0; rowIndex--, colIndex--) {
            if (board[rowIndex][colIndex] == 'Q') {
                return false;
            }
        }

        int length = board[0].length;
        for (int rowIndex = i - 1, colIndex = j + 1; rowIndex >= 0 && colIndex < length; rowIndex--, colIndex++) {
            if (board[rowIndex][colIndex] == 'Q') {
                return false;
            }
        }

        return true;
    }

}
