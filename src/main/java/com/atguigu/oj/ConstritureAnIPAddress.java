package com.atguigu.oj;

import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/16 23:19
 */
public class ConstritureAnIPAddress {
    /**
     * 一个IPV4地址可以用点分十进制表示, 格式为 x1.x2.x3.x4.对合法的定义简化为:
     * x1,x2,x3,x4的合法范围均为 [0,255],分隔符是字符
     * 不考虑IP地址的具体含义, 比如 0.0.0.0 255.255.255.255 也是合法的.
     * 现给定一个仅含有数字字符的字符串,请问由这些数字字符,配合分隔符一共可以能够组成多少个不同的IPV4地址.
     * 每个数字字符可以重复使用.
     * <p>
     * ip地址不含有前导0 8.8.8.8 和08.08.08.008是一致的.
     * <p>
     * 23
     * 输出 10000
     * <p>
     * 由2,3可以组成10个不同的数字
     * 2 3 22  23 32 33 222 223 232 233
     * 每个字段都有10个组合.一共10^4
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nums = scanner.nextLine();
        scanner.close();
        System.out.println(composeIpAddress(nums));
    }

    private static int composeIpAddress(String nums) {
        String[] numsArr = nums.split("");
        int count = 0;
        for (int i = 0; i < 256; i++) {
            String oneStr = String.valueOf(i);
            if (isValid(oneStr, nums)) {
                count++;
            }
        }
        return (int) Math.pow(count, 4);
    }

    private static boolean isValid(String oneStr, String numsStr) {
        // for (int i = 0; i < oneStr.length(); i++) {
        //     String oneCharStr = oneStr.substring(i, i + 1);
        //     // oneStr中的字符要全部来自numsStr
        //     if (!numsStr.contains(oneCharStr)) {
        //         return false;
        //     }
        // }
        //
        char[] oneStrArr = oneStr.toCharArray();
        for (char aChar : oneStrArr) {
            if(numsStr.indexOf(aChar)== -1){
                return false;
            }
        }
        return true;
    }
}
