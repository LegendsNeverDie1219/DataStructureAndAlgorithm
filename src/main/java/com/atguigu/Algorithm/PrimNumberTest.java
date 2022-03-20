package com.atguigu.Algorithm;

import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/14 20:10
 */
public class PrimNumberTest {
    /**
     * 求100以内的所有质数,并打印
     */
    @Test
    public void tesPrimNumberOne() {
        // 被除数范围 2-100(最小的质数是2)
        long startTime = System.currentTimeMillis();
        int count = 0;
        for (int i = 2; i <= 100; i++) {
            boolean flag = true;
            // 除数范围(2 ~ i-1)只要有一次能整除 ,则flag为false, 跳出内层for循环,
            // 优化二:
            for (int j = 2; j <= Math.sqrt(i); j++) {
//            for (int j = 2; j < i; j ++) {
                if (i % j == 0) {
                    flag = false;
                    // 优化一: 添加break;
                    // 默认跳过包裹该关键字的最近的一层for循环.
                    break;
                }
            }
            if (flag) {
                //System.out.println("质数: " + i);
                count++;
            }
        }
        System.out.println("质数的个数为: " + count);
        System.out.println("话费的时间为: " + (System.currentTimeMillis() - startTime));
        // 第一次: 13072
        // 添加break :1091
        // 修改循环判断条件:59
    }

    /**
     * 求100以内的所有质数,并打印
     */
    @Test
    public void tesPrimNumberTwo() {
        // 被除数范围 2-100(最小的质数是2)
        long startTime = System.currentTimeMillis();
        int count = 0;
        name:
        for (int i = 2; i <= 100; i++) {
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    // 默认跳过包裹该关键字的最近的一层for循环.
                    // 如果想跳过外层for循环,需要给外层for循环起别名, continue label
                    continue name;
                }
            }
            count++;
        }
        System.out.println("质数的个数为: " + count);
        // 9592 话费的时间为: 11
        System.out.println("话费的时间为: " + (System.currentTimeMillis() - startTime));
    }
}
