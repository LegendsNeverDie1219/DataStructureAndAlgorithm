package com.atguigu.oj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/4 23:04
 */
public class SendCDRs {
    public static void main(String[] args) {
        // 110
        // 5
        // 50 20 30 10 50
        // 2   2  1  3  1

        // 3
        Scanner scanner = new Scanner(System.in);
        int cap = scanner.nextInt();
        int line = scanner.nextInt();
        int[][] billPrior = new int[line][2];
        for (int i = 0; i< line;i++) {
            billPrior[i][1] = scanner.nextInt();
        }

        for(int i = 0; i < line;i++) {
            billPrior[i][0] = scanner.nextInt();
        }
        scanner.close();
        int result = getMaxSendNumber(cap, billPrior);
        System.out.println(result);
    }

    private static int getMaxSendNumber(int cap, int[][] billPrior) {
        Arrays.sort(billPrior, new Comparator<int[]>() {
            @Override
            public int compare(int[] obj1, int[] obj2) {
                if (obj1[1] == obj2[1]) {
                    return obj1[0] - obj2[0];
                }
                return obj1[1] - obj2[1];
            }
        });

        int count = 0;
        int result = cap;
        for (int i = 0; i < billPrior.length; i++) {
            if (result - billPrior[i][1] >= 0) {
                result = result - billPrior[i][1];
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
