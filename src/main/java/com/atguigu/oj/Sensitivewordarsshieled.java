package com.atguigu.oj;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/5/22 9:10
 */
public class Sensitivewordarsshieled {
    /**
     *敏感词中的各字符顺序地出现在待检查的字符串中,且第一个字符和最后一个字符之间的其他字符个数小于
     * 敏感词长度,联通其他字符一起替换为*.
     * 如:敏感词135可匹配字符串
     * 1?3?5 ,1??35,不匹配1?3??
     * 支持贪婪匹配.即每次从左到右扫描,一旦遇到可匹配的情况就开始进行匹配,
     * 且匹配尽可能长的字符.
     * 例如: 敏感词为abc时,字符串 aabcbc 应该被替换为*bc 而不是a*
     *
     * 输入:
     * ABCdfgABC
     * ABC
     *
     * 输出:
     * *dfg*
     *
     * 输入:
     * abbdefghjjk
     * bdfhj
     *
     * 输出;
     * a*k
     *
     * 输入:
     * abbbbbcde
     * abc
     * 输出:
     * abbbbbcde
     * a和c之间的字符串bbbbb长度 ? 敏感词的长度,因此无法屏蔽.
     *
     *
     * @param args args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String checkedStr = scanner.nextLine();
        String sensitiveStr = scanner.nextLine();
        scanner.close();
        String result = getMaskedStr(checkedStr,sensitiveStr);
        System.out.println(result);
    }

    private static String getMaskedStr(String checkedStr, String sensitiveStr) {
        // 1.双指针,
        // left从左往右扫描,如果left指针发现一个敏感词的首字母,则开始就进行匹配,
        // 此时right指针从右往左扫描.如果right指针发现一个敏感词的尾字母,则开始判断该[left,right]是否可以被敏感词完全匹配
        // 如果可以,则将[i,j]的范围进行替换为一个* .然后对替换后的新字符串再次执行上述 双指针的匹配过程.
        for(int left = 0; left < checkedStr.length();left++) {
            if(checkedStr.charAt(left) == sensitiveStr.charAt(0)) {
                for(int right = checkedStr.length()-1; right>=left; right--) {
                    if(checkedStr.charAt(right) == sensitiveStr.charAt(sensitiveStr.length()-1)) {
                        // 此刻 [left,right] 对应的区间 即为要判断的.
                        String subStr = checkedStr.substring(left,right+1);
                        if(isSensitiveStr(subStr,sensitiveStr)) {
                           // String replaceStr = checkedStr.replace(subStr, "*");
                            StringBuilder sb = new StringBuilder(checkedStr);
                            String replaceStr = sb.replace(left, right + 1, "*").toString();

                            return getMaskedStr(replaceStr,sensitiveStr);
                        }
                    }
                }
            }
        }
        return checkedStr;
    }

    private static boolean isSensitiveStr(String subStr, String sensitiveStr) {
        if(subStr.length()>= sensitiveStr.length()*2) {
            return false;
        }
        Queue<Character> sensitiveQueue = new LinkedList<>();
        for (char ch : sensitiveStr.toCharArray()) {
            sensitiveQueue.offer(ch);
        }

        for (char subStrCh : subStr.toCharArray()) {
            if(!sensitiveQueue.isEmpty() && sensitiveQueue.peek().equals(subStrCh) ) {
                sensitiveQueue.poll();
            }
        }

        return sensitiveQueue.isEmpty();
    }
}
