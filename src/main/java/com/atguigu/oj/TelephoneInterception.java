package com.atguigu.oj;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/17 5:30
 */
public class TelephoneInterception {
    // C 13000... 表示一条电话呼入记录
    // W 13140000 表示添加一个号码到白名单中
    // W 03712832* 表示以03712832 开头的号码都为 白名单(* 为通配符,且仅在结尾.)
    // 请按照给定的过程记录.分别统计每个呼入号码的接通和拒接次数.
    // 如: 131444444 1 3 表示号码 13144444 接通1次,拒接3次.
    // 统计记录的输出顺序,要按照给定记录中号码的首次呼入出现的先后顺序.

    // 7
    // C 133000000
    // W 131444444
    // C 131444444
    // C 03712832444
    // C 03712832233
    // W 03712832*
    // C 03712832444

    // 输出:
    // 133000000 0 1
    // 131444444 1 0
    // 0371283244 1 1
    // 0371283223 0 1
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int recordNum = Integer.parseInt(scanner.nextLine());
        List<String> records = new ArrayList<>(recordNum);
        for (int i = 0; i < recordNum; i++) {
            records.add(scanner.nextLine());
        }
        scanner.close();
        String[][] results = getPhoneRecord(records);
        for (String[] result : results) {
            System.out.println(String.join(" ", result));
        }
    }

    private static String[][] getPhoneRecord(List<String> records) {
        Set<String> regexWhiteList = new HashSet<>();
        Set<String> whiteList = new HashSet<>();
        // 保证遍历的时候有顺序.
        LinkedHashMap<String, int[]> callRecordMap = new LinkedHashMap<>();
        for (String record : records) {
            String[] strArr = record.split(" ");
            String phone = strArr[1];
            if ("W".equals(strArr[0])) {
                // 加入到白名单
                addToWhiteList(phone, regexWhiteList, whiteList);
            } else {
                // 尝试呼叫
                tryToCall(phone, regexWhiteList, whiteList, callRecordMap);
            }
        }

        Set<Map.Entry<String, int[]>> entries = callRecordMap.entrySet();
        List<Map.Entry<String, int[]>> collect = new ArrayList<>(entries);
        String[][] resultArr = new String[collect.size()][3];
        for (int i = 0; i < collect.size(); i++) {
            Map.Entry<String, int[]> entry = collect.get(i);
            resultArr[i][0] = entry.getKey();
            resultArr[i][1] = String.valueOf(entry.getValue()[0]);
            resultArr[i][2] = String.valueOf(entry.getValue()[1]);
        }
        return resultArr;
    }

    private static void tryToCall(String phone, Set<String> regexWhiteList, Set<String> whiteList,
                                  Map<String, int[]> callRecordMap) {
        int[] intArray = callRecordMap.getOrDefault(phone, new int[2]);
        boolean flag = true;
        for (String regexPhone : regexWhiteList) {
            if (phone.startsWith(regexPhone)) {
                // 接通增加一次
                intArray[0]++;
                flag = false;
                break;
            }
        }

        // 添加flag,确保不会重复添加.
        if (flag && whiteList.contains(phone)) {
            intArray[0]++;
            flag = false;
        }

        if (flag) {
            // 拒接增加一次
            intArray[1]++;
        }

        // 添加新的记录.或者更新结果.
        callRecordMap.put(phone, intArray);
    }

    private static void addToWhiteList(String phone, Set<String> regexWhiteList, Set<String> whiteList) {
        if (phone.endsWith("*")) {
            // String str = phone.replaceAll("\\*", "");
            String noRegexPhone = phone.replace("*", "");
            if (regexWhiteList.isEmpty()) {
                regexWhiteList.add(noRegexPhone);
            } else {
                // 遍历已存在的正则白名单, 如果存在一个相等的或者更短的,则丢弃本次添加. 如果存在一个更长的,则移除之前的,添加本次的.
                regexWhiteList.removeIf(existRegexPhone ->
                        noRegexPhone.length() < existRegexPhone.length() && existRegexPhone.startsWith(noRegexPhone));
                regexWhiteList.add(noRegexPhone);
            }
        } else {
            whiteList.add(phone);
        }
    }
}
