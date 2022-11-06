package com.atguigu.oj;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/15 17:39
 */
public class SensitiveLog {
    // 敏感词的屏蔽规则:
    // 1.key 为password 或pwd的, 对应的value 统一用6个* 替换.
    // 2.key 在word中, 且以IP(不区分大小写)结尾的.对应的value中ip地址中间两段分别用3个*替代.(不用考虑IPV6格式)
    // 3.key 在word中. 且以非IP结尾的.仅需要对value中最右侧的长度(L)大于等于4.且连续为数字的子串进行屏蔽.从倒数
    // 第L/4 + 1个 数字字符开始(比如L 为9 ,从倒数第3个开始.) 从右到左对中间的L/2个字符用"* 替换

    // 输入
    // 首行为一个正整数 N .表示敏感信息关键字的个数.
    // 第二行为一个长度为N的一维数组, 表示敏感信息关键字列表.words.
    // 第三行为一个字符串.表示待处理的日志信息. 字符串长度范围.

    // 输出:
    // 一个字符串, 表示屏蔽后的日志信息.

    // 5
    // IMSI CellID UserIP ..
    //Apn:cmnet,Qos:121212121212,CellID:46564645

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        String[] keys = scanner.nextLine().split(" ");
        String log = scanner.nextLine();
        scanner.close();

        String result = logAnonymize(keys, log);
        System.out.println(result);
    }

    private static String logAnonymize(String[] keys, String log) {
        // 1.屏蔽关键字全部转换为大写/小写 以方便与log进行比较. 并且数组 转换为集合.以便调用containsAPI
        List<String> keyList =
                Arrays.stream(keys).map(item -> item.toUpperCase(Locale.ROOT)).collect(Collectors.toList());

        String[] logArray = log.split(",");
        StringBuilder sb = new StringBuilder();
        for (String logEntry : logArray) {
            String[] logEntryArr = logEntry.split(":");
            String upperCaseKey = logEntryArr[0].toUpperCase(Locale.ROOT);
            String value = logEntryArr[1];

            if (!sb.toString().isEmpty()) {
                sb.append(",");
            }
            sb.append(logEntryArr[0]).append(":");
            if ("password".equalsIgnoreCase(upperCaseKey) || "pwd".equalsIgnoreCase(upperCaseKey)) {
                sb.append("******");
            } else if(keyList.contains(upperCaseKey) && upperCaseKey.endsWith("IP")) {
                String[] ipAddressArr = value.split("\\.");
                sb.append(ipAddressArr[0]).append(".***.***.").append(ipAddressArr[3]);
            } else if(keyList.contains(upperCaseKey)) {
                sb.append(getNumInfo(value));
            } else {
                sb.append(value);
            }
        }
        return sb.toString();
    }


    private static String getNumInfo(String value) {
        return "";
    }
}
