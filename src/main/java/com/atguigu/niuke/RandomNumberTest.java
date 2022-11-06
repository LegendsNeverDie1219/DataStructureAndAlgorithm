package com.atguigu.niuke;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RandomNumberTest
 *
 * @author Administrator
 * @date 2022/3/26 12:39
 */
public class RandomNumberTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入N的个数 N[1,1000]");
        int count = Integer.parseInt(scanner.nextLine());
        Set<Integer> numberSet = new HashSet<>();
        for (int i = 0; i < count; i++) {
           System.out.println("请输入第" + (i+1)+ "个数字, Number[1,500]");
            int number = Integer.parseInt(scanner.nextLine());
            if (number < 1 || number > 500) {
                System.out.println("你输入的数字不合法,退出");
                break;
            }
            numberSet.add(number);
        }
        List<Integer> collect =
                numberSet.stream().sorted(Comparator.comparingInt(number -> number)).collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
