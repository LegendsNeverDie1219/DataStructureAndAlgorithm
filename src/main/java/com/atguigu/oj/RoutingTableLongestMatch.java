package com.atguigu.oj;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/4 20:26
 */
public class RoutingTableLongestMatch {
    public static void main(String[] args) {
        // 样例1
        // 192.168.0.03
        // 6
        // 10.166.50.0/23
        // 192.0.0.0/8
        // 10.255.255.255/32
        // 192.168.0.1/24
        // 127.0.0.0/8
        // 192.168.0.0/24

        // 输出: 192.168.0.01/24

        // 样例三
        // 10.110.32.77
        // 2

        // 127.0.0.1/8
        //0.0.0.0./0

        //输出: 0.0.0.0
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        System.out.println("请输入目的IP地址");
        String dstIp = scanner.nextLine();
        int row = Integer.parseInt(scanner.nextLine());
        int col = 2;
        String[][] ipTable = new String[row][col];
        for(int i = 0; i <ipTable.length; i++) {
            String[] currentItem = scanner.nextLine().split("/");
            System.arraycopy(currentItem,0,ipTable[i],0,col);
        }
        scanner.close();

        String result = routeSearch(dstIp,ipTable);
        System.out.println(result);
    }

    private static String routeSearch(String dstIp, String[][] ipTable) {
        // 样例三
        // 10.110.32.77
        // 2

        // 127.0.0.1/8
        //0.0.0.0./0

        //输出: 0.0.0.0/0
        // 找到匹配的那一条路由记录.
        int maxRouteItem = -1;
        int maxMask = -1; //todo 这个必须要取一个小于0的值.
        for (int index = 0; index < ipTable.length; index++) {
          String routeIp = ipTable[index][0];
          int mask = Integer.parseInt(ipTable[index][1]);
          if(isMatched(dstIp, routeIp,mask)) {
              // 更新掩码的最大值.
              if (mask > maxMask) {
                  maxMask = mask;
                  maxRouteItem = index;
              }
          }
        }

        if (maxMask == -1) {
            return "empty";
        }
        return ipTable[maxRouteItem][0] + "/" + ipTable[maxRouteItem][1];
    }

    private static boolean isMatched(String dstIp, String routeIp, int mask) {
        // 10.110.32.77
        String dstIpStr = getBinaryString(dstIp);
        String dstIpSubStr = dstIpStr.substring(0, mask);

        String routeIpStr = getBinaryString(routeIp);
        String routeIpSubStr = routeIpStr.substring(0, mask);

        return dstIpSubStr.equals(routeIpSubStr);
    }

    private static String getBinaryString(String dstIp) {
        String[] dstIpArr = dstIp.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String dstIpStr : dstIpArr) {
            int dstIpNumber = Integer.parseInt(dstIpStr);
            String dstIpNumberBinaryStr = Integer.toBinaryString(dstIpNumber);

            // todo :目标ip二进制字符串前面补零 直到为 8位
            for(int i = 0; i < 8-dstIpNumberBinaryStr.length();i++) {
                sb.append("0");
            }

            sb.append(dstIpNumberBinaryStr);
        }
        return sb.toString();
    }
}
