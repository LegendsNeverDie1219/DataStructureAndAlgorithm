package com.atguigu.niuke;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/26 13:00
 */

/**
 * Created by 华夏紫云 on 2015/11/23.
 */
public class BaseConvert {
    public static void main(String[] args) {
        // Scanner sc=new Scanner(System.in);
        // while (sc.hasNext()){
        //     String str=sc.next().substring(2);
        //     System.out.println(Integer.parseInt(str,16));
        // }

        // 十进制转化为其他进制
        String binaryString = Integer.toBinaryString(10);
        System.out.println("binaryString: " + binaryString);

        String octalString = Integer.toOctalString(10);
        System.out.println("octalString: " + octalString);

        String hexString = Integer.toHexString(10);
        System.out.println("hexString: " + hexString);

        // 其他进制转化为10进制
        int resultOne = Integer.parseInt("10", 2);
        System.out.println("resultOne: " + resultOne);
        int resultTwo = Integer.parseInt("10", 8);
        System.out.println("resultTwo: " + resultTwo);
        int resultThree = Integer.parseInt("10", 16);
        System.out.println("resultThree: " + resultThree);

        double pow1 = Math.pow(9, 2);
        System.out.println("pow1: " + pow1);

        double pow2 = Math.pow(9, 1.0 / 2);
        System.out.println("pow1: " + pow2);

    }
}