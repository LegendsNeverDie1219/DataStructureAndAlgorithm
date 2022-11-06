package com.atguigu.oj;

import java.util.Arrays;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/18 23:33
 */
public class PromotionalActivities {
    /**
     * 某一秒内最早的订单,可以获取免单,(可能有多个)
     * 现在给定一批订单记录, 请计算有多少个订单可以免单.
     *
     * 下单格式YYYY-MM-DD hh:mm:ss:fff
     *
     * 3
     * 2019-01-01 00:00:00.001
     * 2019-01-01 00:00:00.002
     * 2019-01-01 00:00:00.003
     * 2019-01-01 00:00:00.003
     * 2019-01-01 00:00:00.003
     * 2019-01-01 00:00:00.003
     *1
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int row =scanner.nextInt();
        scanner.nextLine();
        String[] orderTime = new String[row];
        for(int i = 0; i <row;i++) {
            orderTime[i] = scanner.nextLine();
        }
        scanner.close();
        int result = freeOrder(orderTime);
        System.out.println(result);
    }

    private static int freeOrder(String[] orderTime) {
        int count = 0;
        Arrays.sort(orderTime);
        String firstOrder = orderTime[0];
        String prefix = firstOrder.split("\\.")[0];
        for (String str : orderTime) {
            // 不再同一秒内.更新prefix.更新count
            if (!str.startsWith(prefix)) {
                firstOrder = str;
                prefix = str.split("\\.")[0];
                count++;
            } else {
                // 是同一秒,并且和同一秒内的最早记录相等. 则添加一次记录(如果有多条记录,就添加多次)
                if (firstOrder.equals(str)) {
                    count++;
                }
            }
        }
        return count;
    }
}
