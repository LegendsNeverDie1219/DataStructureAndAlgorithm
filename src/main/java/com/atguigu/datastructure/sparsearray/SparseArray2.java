package com.atguigu.datastructure.sparsearray;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/2/9 22:44
 */
public class SparseArray2 {
    private static final String FILE_PATH =
            System.getProperty("user.dir") + File.separator + "JavaModuleOne" + File.separator +
                    "array_data1219.txt";

    /**
     * 当一个数组中大部分元素为0或者为同一个值的时候， 可以使用稀疏数组来 代替 原有数组
     * 目的： 减少内存空间
     * 二维数组转化为稀疏数组思路：
     * 1.遍历原来的二维数组，得到有效数据的个数 sum
     * 2.根据sum 就可以创建（new) 一个稀疏数组 new int[sum + 1][3]
     * 3.将二维数组的有效数据存入到稀疏数组中
     * <p>
     * 稀疏数组转化为二维数组
     * 1.先读稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，
     * 2. 再读取稀疏数组的后几行数据， 然后赋值给原始的二维数组即可。
     */

    public static void main(String[] args) {
        //  输出原始的二维数组
        int[][] chessArray = getSourceArray();
        //printTwoDimensionalArray(chessArray);

        // 将原始的二维数组转化为稀疏数组
        int[][] sourceArray = getSourceArray();
        int[][] sparseArray = convert2SparseArray(sourceArray);
        printTwoDimensionalArray(sparseArray);
        // 将数组转化输出流存入到磁盘中。
        writeToDisk(sparseArray);
        int[][] sparseArray2 = readFromDisk(FILE_PATH);
        int[][] normalArray = convert2NormalArray(sparseArray2);
        printTwoDimensionalArray(normalArray);
    }

    private static int[][] convert2NormalArray(int[][] sparseArray) {
        int length = sparseArray[0][0];
        int width = sparseArray[0][1];
        int[][] normalArray = new int[length][width];
        for (int i = 1; i < sparseArray.length; i++) {
            normalArray[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return normalArray;

    }

    private static int[][] readFromDisk(String filePath) {
        List<int[]> arrayList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)))) {
            String str = bufferedReader.readLine();
            while (str != null) {
                // todo 重要 String -> strArr
                String[] strArr = str.split(" ");
                // String[] -> Stream[String] -> IntStream -> int[]
                int[] intArray = Arrays.stream(strArr).mapToInt(Integer::valueOf).toArray();
                arrayList.add(intArray);
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] sparseArray = new int[arrayList.size()][3];
        for (int i = 0; i < arrayList.size(); i++) {
            sparseArray[i] = arrayList.get(i);
        }
        return sparseArray;
    }

    private static void writeToDisk(int[][] sparseArray) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(FILE_PATH)))) {
            for (int[] row : sparseArray) {
                // todo 重要 int[] -> IntStream -> Stream<String> -> String
                String str = Arrays.stream(row)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" "));
                bufferedWriter.write(str + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printTwoDimensionalArray(int[][] twoDimensionalArray) {
        for (int[] row : twoDimensionalArray) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }


    private static int[][] convert2SparseArray(int[][] sourceArray) {
        // 1.遍历原来的二维数组，得到有效数据的个数 sum
        int sum = 0;
        for (int[] row : sourceArray) {
            for (int data : row) {
                if (data != 0) {
                    sum++;
                }
            }
        }
        // 2.根据sum 就可以创建（new) 一个稀疏数组 new int[sum + 1][3]
        int[][] sparseArray = new int[sum + 1][3];
        // 3.将二维数组的有效数据存入到稀疏数组中
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        int count = 1;
        for (int i = 0; i < sourceArray.length; i++) {
            for (int j = 0; j < sourceArray[i].length; j++) {
                if (sourceArray[i][j] != 0) {
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = sourceArray[i][j];
                    count++;
                }
            }
        }
        return sparseArray;
    }

    private static int[][] getSourceArray() {
        // 创建一个原始的二维数组，
        // 0 表示没有棋子， 1 表示该位置是黑子， 2 表示该位置是蓝子
        int[][] chessArray = new int[11][11];
        chessArray[1][2] = 1;
        chessArray[2][3] = 2;
        chessArray[4][5] = 2;
        return chessArray;
    }
}
