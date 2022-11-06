package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/1 14:19
 */
public class VersionUpgrade {
    /**
     * 输入:
     * 第一行一个整数
     * 第二行字符串表示已发布的版本号,多个版本号 以 单空格 分隔开,
     * 第三行是给定的新版本号.
     * <p>
     * 注意: 已发布的版本列表中没有重复的版本号.
     * <p>
     * 输出:
     * 一个整数, 表示需要生计的版本数.
     * <p>
     * 输入样例1
     * 7
     * 100.200    20. 500    100.5   100.05.0   0.0.0.0    100.50.1      100.50.0
     * 100.200 20.500 100.5 100.05.0 0.0.0.0 100.50.1 100.50.0
     * 100.50
     * 输出样例:
     * 4
     * 要升级的为 20.500  0.0.0.0   100.5  100.05.0
     * <p>
     * 输入样例2:
     * 0
     * <p>
     * 5.0.0
     * 输出样例
     * 0
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        System.out.println("请输入已经存在的旧版本号的个数:");
        int versionNumber = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("请输入具体的旧版本信息, 以空格隔开");
        String[] oldVersions = scanner.nextLine().split(" ");
        String newVersion = scanner.nextLine();
        int result = versionUpgrade(oldVersions, newVersion);
        System.out.println(result);

    }

    private static int versionUpgrade(String[] oldVersions, String newVersion) {
        // 问题的关键在于:新版本字符串的段数  和旧版本字符串的段数不一致,有可能大,或者小,或者相等.
        // 所以需要将 新版本的字符串 和旧版本的字符串段数保持一致.才可以更好的比较
        // 旧版本字符串 长度大 则需要截取段数 到newVersion.length+1的长度.(其实不需要截断,比较到newVersion.length+1 就知道结果了.)
        // 旧版本字符串 长度小, 则需要补充段数 到newVersion.length+1 的长度.
        List<String> upgradeVersions = new ArrayList<>();
        String[] newVersionStrArr = newVersion.split("\\.");
        List<String> newVersionStrList = Arrays.stream(newVersionStrArr)
                .collect(Collectors.toList());
        newVersionStrList.add("0");
        for (String oldVersion : oldVersions) {
            List<String> oldVersionStrList = fillZeroAndConvert2List(newVersionStrList, oldVersion);
            for (int index = 0; index < newVersionStrList.size(); index++) {
                int oldValue = Integer.parseInt(oldVersionStrList.get(index));
                int newValue = Integer.parseInt(newVersionStrList.get(index));
                if (oldValue > newValue) {
                    // 不需要升级
                    break;
                }
                if (oldValue < newValue) {
                    //需要升级
                    String oldVersionStr = String.join(".", oldVersionStrList);
                    upgradeVersions.add(oldVersionStr);
                    break;
                }
            }
        }
        upgradeVersions.forEach(System.out::println);
        return upgradeVersions.size();
    }

    private static List<String> fillZeroAndConvert2List(List<String> newVersionStrList, String oldVersion) {
        String[] oldVersionArr = oldVersion.split("\\.");
        List<String> oldVersionStrList = new ArrayList<>();
        for (int i = 0; i< newVersionStrList.size();i++) {
            if (i < oldVersionArr.length) {
                oldVersionStrList.add(oldVersionArr[i]);
            } else {
                oldVersionStrList.add("0");
            }
        }
        return oldVersionStrList;
    }
}
