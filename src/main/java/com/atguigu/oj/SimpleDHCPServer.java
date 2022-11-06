package com.atguigu.oj;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/18 21:53
 */
public class SimpleDHCPServer {
    /**
     * DHCP服务器的功能是为每一个MAC地址.分配唯一的IP地址,现在假设,分配的IP地址范围从
     * 192.168.0.0~192.168.0.255 总共256个可用地址.请实现一个简易的DHCP服务器.功能如下:
     * <p>
     * 分配Request :根据输入的MAC地址分配IP地址池中的IP地址.
     * 如果对应的IP已分配并未释放,则为重复申请,直接返回对应已经分配的IP地址.
     * 如果一个MAC地址已经申请过并且已经释放,即当前未分配IP地址,则为再次申请,优先分配最近一次曾经为其分配过的IP地址.请返回
     * 此地址.
     * 按升序分配从未分配过的IP地址,如果地址池中地址都已经被分配过, 则按照升序分配已经释放出来的IP地址,若可以分配成功,则返回
     * 此地址.
     * 若仍然无法分配成功,则返回NA
     * 释放Release: 根据输入的MAC地址释放已经分配的IP地址.
     * 如果申请释放的对应IP地址,已经分配,则释放该IP地址.
     * 如果申请释放的对应的IP地址不存在,则不做任何事情.
     * <p>
     * 输入:
     * 首行为整数n,表示之后有n行命令行
     * 之后每行为一条分配的命令.
     * <p>
     * 命令只有两种
     * REQUEST和RELEASE.
     * MAC地址为: 12个大写英文字母或者数字. 如AABBCCDDEEF1
     * 输出:
     * 1.REQUEST 命令: 输出分配的结果,(IP地址字符串或者NA)
     * 2. RELEASE 命令, 不输出任何内容.
     * <p>
     * 输入样例1
     * REQUEST=AABBCCDDEEF1
     * REQUEST=F2FBBCCDDEEF
     * RELEASE=AABBCCDDEEF1
     * RELEASE=F2FBBCCDDEEF
     * REQUEST=333333333333
     * REQUEST=F2FBBCCDDEEF
     * 输出样例
     * <p>
     * 192.168.0.0
     * 192.168.0.1
     * 192.168.0.2
     * 192.168.0.1
     */

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        DhcpServer dhcpServer = new DhcpServer();
        int operationCnt = cin.nextInt();
        for (int i = 0; i < operationCnt; i++) {
            String[] operation = cin.next().split("=");
            if ("REQUEST".equals(operation[0])) {
                System.out.println(dhcpServer.request(operation[1]));
            } else {
                dhcpServer.release(operation[1]);
            }
        }
        cin.close();
    }

    private static class DhcpServer {
        // 记录 mac地址-> 该mac地址分配的ip地址  的map
        Map<String, Integer> macIpSuffixMap = new HashMap<>();
        // 记录 mac地址 -> 该mac地址上一次分配的ip地址 的map(仅仅记录上一次)
        Map<String, Integer> historyMacIpSuffixMap = new HashMap<>();
        // 记录该mac地址上一次分配的ip地址 -> mac地址 .
        Map<Integer, String> historyIpSuffixMacMap = new HashMap<>();
        // IP地址的通用前缀
        String commconIpAddressPrefix = "192.168.0.";
        // IP地址的初始化后缀.
        int ipSuffixNum = -1;

        /**
         * 输入macAddress输出为其分配的ip地址.
         *
         * @param macAddress macAddress
         * @return ip地址/NA
         */
        public String request(String macAddress) {
            // 1.先去缓存中看是否存在 该map地址 -> 对应的ip地址.,存在,则返回
            if (macIpSuffixMap.containsKey(macAddress)) {
                return commconIpAddressPrefix + macIpSuffixMap.get(macAddress);
            }
            // 2.再去上一次的释放的历史记录里面看 是否存在, 存在,则将历史记录的那次 添加到分配的ip地址池中, 移除历史记录.返回该地址.
            if (historyMacIpSuffixMap.containsKey(macAddress)) {
                Integer ipSuffix = historyMacIpSuffixMap.get(macAddress);
                // 添加并移除
                addAndRemove(macAddress, ipSuffix);

                return commconIpAddressPrefix + ipSuffix;
            }
            // 3. 以上都没有,则为首次分配, 为该mac地址分配一个新的ip.递增的形式,分配成功,则返回true
            boolean isSuccess = allocateIpaddress4Mac(macAddress);

            if (isSuccess) {
                return commconIpAddressPrefix + macIpSuffixMap.get(macAddress);
            }
            // 4.如果分配失败. 则判断地址池是否满了,如果满了,则返回NA
            if (macIpSuffixMap.size() >= 256) {
                return "NA";
            }
            // 5.没有满,则从释放的IP地址历史记录中找到一个IP地址最小的, 进行分配.
            Integer ipSuffix = historyIpSuffixMacMap.keySet().stream().sorted().limit(1).findFirst().get();
            addAndRemove(macAddress, ipSuffix);

            return commconIpAddressPrefix + ipSuffix;
        }

        private boolean allocateIpaddress4Mac(String macAddress) {
            if (ipSuffixNum < 255) {
                ipSuffixNum++;
                macIpSuffixMap.put(macAddress, ipSuffixNum);
                return true;
            }
            return false;
        }

        private void addAndRemove(String macAddress, Integer historyFirstIpNum) {
            // 添加并移除
            macIpSuffixMap.put(macAddress, historyFirstIpNum);
            historyMacIpSuffixMap.remove(macAddress);
            historyIpSuffixMacMap.remove(historyFirstIpNum);
        }

        public void release(String macAddress) {
            if (macIpSuffixMap.containsKey(macAddress)) {
                Integer ipSuffix = macIpSuffixMap.get(macAddress);
                // 添加并移除.
                historyMacIpSuffixMap.put(macAddress, ipSuffix);
                historyIpSuffixMacMap.put(ipSuffix, macAddress);
                macIpSuffixMap.remove(macAddress);
            }
        }
    }
}
