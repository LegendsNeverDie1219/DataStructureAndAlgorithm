package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/4 19:38
 */
public class DailyActiveMonthlyActiveStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        System.out.println("请输入日志的行数");
        int num = Integer.parseInt(scanner.nextLine());
        String[] logs = new String[num];
        for (int i = 0; i < num; i++) {
            logs[i] = scanner.nextLine();
        }
        scanner.close();
        int[] results = getActiveUserNum(logs);
        String[] strResults = Arrays.stream(results)
                .mapToObj(String::valueOf).toArray(String[]::new);
        System.out.println(String.join(",", strResults));

    }

    private static int[] getActiveUserNum(String[] logs) {
        // 2020-12-01|192.168.218.001|/log.do|success
        Map<Integer, Set<String>> everyDayServerIpMap = new HashMap<>();
        Set<String> everyMonthServerIp = new HashSet<>();
        for (String oneLog : logs) {
            String[] oneLogArr = oneLog.split("\\|");
            if (!"/log.do".equals(oneLogArr[2])) {
                continue;
            }

            if (!"success".equals(oneLogArr[3])) {
                continue;
            }
            String serverIp = formatServerIp(oneLogArr[1]);
            everyMonthServerIp.add(serverIp);
            Integer day = Integer.valueOf(oneLogArr[0].split("-")[2]);
            if (!everyDayServerIpMap.containsKey(day)) {
                Set<String> oneDayServerIps = new HashSet<>();
                oneDayServerIps.add(serverIp);
                everyDayServerIpMap.put(day, oneDayServerIps);
            } else {
                Set<String> oneDayServerIps = everyDayServerIpMap.get(day);
                oneDayServerIps.add(serverIp);
            }
        }
        // 第一位是月活跃总机器数量(去重) 剩下的是每日活跃的总机器数量
        int[] result = new int[32];
        result[0] = everyMonthServerIp.size();
        Set<Map.Entry<Integer, Set<String>>> entrySet = everyDayServerIpMap.entrySet();
        for (Map.Entry<Integer, Set<String>> oneDayEntry : entrySet) {
            int day = oneDayEntry.getKey();
            result[day] = oneDayEntry.getValue().size();
        }
        return result;

    }

    private static String formatServerIp(String serverIp) {
        // 192.168.218.001 与 192.168.218.1 都转化为 192.168.218.1
        String[] serverIpArr = serverIp.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String s : serverIpArr) {
            Integer ipNumber = Integer.valueOf(s);
            sb.append(ipNumber).append(".");
        }
        return sb.substring(0, sb.length() - 1);
    }

}
