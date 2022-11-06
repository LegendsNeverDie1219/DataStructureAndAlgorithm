package com.atguigu.leetcode.ChapterOne;

import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/4/4 11:28
 */
public class UnsignedMoveTest {
    @Test
    public void test() {
        System.out.println("右移");
        // 补码为 11111111  11111111  11111111  11111010
        int num = -5;
        System.out.println(Integer.toBinaryString(num));
        // 有符号右移2位, 最左边补上符号位, 正数补0, 负数补1
        // 11 11111111  11111111  11111111  111110
        System.out.println(Integer.toBinaryString(num >> 2));
        // 无符号右移2位, 不管正数还是负数,最左边都补0
        // 00 11111111  11111111  11111111  111110
        System.out.println(Integer.toBinaryString(num >>> 2));

    }
}
