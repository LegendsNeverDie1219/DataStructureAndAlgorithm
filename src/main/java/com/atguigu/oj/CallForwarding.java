package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/4 21:17
 */
public class CallForwarding {
    public static void main(String[] args) {
        // 状态: idle, busy, no-response ,unreachable

        // 用户可登记的五种呼叫转移.
        // type 0- : 无条件转移
        // type 1: 用户状态为busy 时触发
        // type 2: 用户状态 no-response触发
        // type 3: 用户状态为 unreachable时触发
        // type 4: 默认转移,优先级最低, 用户不是idle状态, 且无法触发上述四种状态. 触发次转移.
        // 同一状态可以登记多次, 以最后一次为主.

        // 样例一
        // 3 busy

        // 2 1188
        // 4 189
        // 4 190

        // 输出 190

        // 样例二
        // 1 no-response
        // 3 075
        // failure

        //样例三
        // 1 idle
        // 3 075
        // success
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        String[] first = scanner.nextLine().split(" ");
        int row = Integer.parseInt(first[0]);
        String status = first[1];
        List<RegCallOperate> regCallOperates = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            String[] operate = scanner.nextLine().split(" ");
            RegCallOperate regCallOperate = new RegCallOperate(Integer.parseInt(operate[0]), operate[1]);
            regCallOperates.add(regCallOperate);
        }
        scanner.close();

        String result = calling(status, regCallOperates);
        System.out.println(result);
    }

    private static String calling(String status, List<RegCallOperate> regCallOperates) {
        String resultStr = "";
        // 把一个List<RegCallOperate> 转化为一个Map<integer,String>
        // 这样可以在不遍历的时候,用contains就可以判断 登记的记录类型都有那些. 其中为0 要直接返回的.
        //登记的呼叫转移类型 -> 手机号码
        Map<Integer, String> regCallMap = regCallOperates.stream().collect(Collectors.toMap(item -> item.type,
                item -> item.number,
                (oldValue, newValue) -> newValue));

        if (regCallMap.containsKey(0)) {
            return regCallMap.get(0);
        }
        switch (status) {
            case "busy":
                return getResultByStatus(1, regCallMap);
            case "no-response":
                return getResultByStatus(2, regCallMap);
            case "unreachable":
                return getResultByStatus(3, regCallMap);
            case "idle":
                return "success";
            default:
                return "error";
        }
    }

    private static String getResultByStatus(int needTypeNumber, Map<Integer, String> regCallMap) {
        if (regCallMap.containsKey(needTypeNumber)) {
            return regCallMap.get(needTypeNumber);
        } else {
            return regCallMap.getOrDefault(4, "failed");
        }
    }

    private static class RegCallOperate {
        int type = -1;
        String number = null;

        RegCallOperate(int type, String number) {
            this.type = type;
            this.number = number;
        }
    }
}
