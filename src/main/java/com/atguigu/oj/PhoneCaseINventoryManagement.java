package com.atguigu.oj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/20 22:41
 */
public class PhoneCaseINventoryManagement {
    /**
     * 每种型号的手机壳的库存数量 存在数组inventory中, 总售价存在数组price中.
     * 每种型号的手机壳的  销售收益 = 销售数量 * (price[i]/inventory[i])
     * <p>
     * 现在给定市场上手机壳的最大需求量demand,请制定最佳的销售策略.以获取最大的总销售收益.并返回该值.
     * 输入:
     * 首行两个正整数 M和N ,M表示手机壳种类的个数,  N表示市场最大需求量.
     * <p>
     * 第2行M个数字, 表示每种型号手机壳的数量, 每个数字的取值范围为
     * 第3行M个数字,每个每种手机壳的总售价.顺序与第2行一一对应.
     * <p>
     * 输出:
     * 浮点数的最大收益.
     * <p>
     * 3 20
     * 18 15.0 10
     * 75.0 72 45
     * <p>
     * 输出:
     * 94.50
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cnt = scanner.nextInt();
        float demand = scanner.nextFloat();
        float[] inventory = new float[cnt];
        for (int i = 0; i < cnt; i++) {
            inventory[i] = scanner.nextFloat();
        }
        float[] price = new float[cnt];
        for (int i = 0; i < cnt; i++) {
            price[i] = scanner.nextFloat();
        }
        scanner.close();
        String result = phoneCaseInventoryManage(demand, inventory, price);
        System.out.println(result);
    }

    private static String phoneCaseInventoryManage(float demand, float[] inventory, float[] price) {
        List<PhoneShellProfit> phoneShellProfitList = new ArrayList<>();
        for (int i = 0; i < inventory.length; i++) {
            PhoneShellProfit phoneShellProfit = new PhoneShellProfit();
            phoneShellProfit.serialNumber = i;
            phoneShellProfit.profit = price[i] / inventory[i];
            phoneShellProfitList.add(phoneShellProfit);
        }
        List<PhoneShellProfit> sortedList = phoneShellProfitList.stream()
                .sorted((o1, o2) -> Float.compare(o2.profit, o1.profit)).collect(Collectors.toList());


        Double totalProfit = 0.0d;
        // while(demand >=0) {
        //     PhoneShellProfit oneTypeMaxProfit = sortedList.get(0);
        //     inventory[]
        //
        // }
        for (PhoneShellProfit phoneShellProfit : sortedList) {
            int index = phoneShellProfit.serialNumber;
            // 单件的利润
            float profit = phoneShellProfit.profit;
            // 花费的总数
            float costNumber = Math.min(demand, inventory[index]);
            totalProfit += costNumber * profit;

            demand -= costNumber;
            if (demand <= 0) {
                break;
            }
        }
        return String.valueOf(totalProfit);

    }

    private static class PhoneShellProfit {
        Integer serialNumber;
        Float profit;
    }
}
