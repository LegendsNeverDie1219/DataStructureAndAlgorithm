package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/26 16:03
 */
public class MinimalCoveringSubstring {
    /**
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。
     * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 注意：
     * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
     * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
     * 示例 1：
     * <p>
     * 输入：s = "ADOBECODEBANC", t = "ABC"
     * 输出："BANC"
     * <p>
     * 示例 2：
     * 输入：s = "a", t = "a"
     * 输出："a"
     * <p>
     * 示例 3:
     * 输入: s = "a", t = "aa"
     * 输出: ""
     * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
     * 因此没有符合条件的子字符串，返回空字符串。
     */
    @Test
    public void testMinimalCoveringSubstring() {
        // String s = "ADOBECODEBANC";
        // String t = "ABC";

        // String s = "a";
        // String t = "a";

        String s = "a";
        String t = "aa";
        String result = minimalCoveringSubstring(s, t);
        System.out.println("result: " + result);
    }

    private String minimalCoveringSubstring(String sourceStr, String targetStr) {
        // 采用滑动窗口解题
        // 1.首先在sourceStr中定义一个划动的窗口, 即定义两个指针left,right,即窗口 为[left,right)
        // 2.right不停的右移,直到窗口中的字符以及字符个数满足了target中的字符 即寻找可行解
        // 3.right停止右移,left不停的右移,直到窗口中的字符以及字符个数不满足target中的字符,
        // 在移动的过程,还会不停的更新解的情况,即寻找最优解

        // 目标字符计数器
        Map<Character, Integer> targetCharacterCounter = getTargetCharacterCounter(targetStr);
        //窗口计数器
        Map<Character, Integer> window = new HashMap<>();
        // 两个指针
        int left = 0;
        int right = 0;
        int valid = 0;

        int startIndex = 0;
        int endIndex = 0;
        int length = Integer.MAX_VALUE;
        while (right < sourceStr.length()) {
            // 将移入窗口的字符
            char moveInChar = sourceStr.charAt(right);
            // 扩大窗口,即right右移
            right++;
            if (targetCharacterCounter.containsKey(moveInChar)) {
                // 该字符为有效字符,则更新窗口计数器
                Integer count = window.getOrDefault(moveInChar, 0);
                window.put(moveInChar, ++count);
                // 如果该字符移入之后,该字符对应的个数刚好就符合要求了, 则valid++
                if (window.get(moveInChar).equals(targetCharacterCounter.get(moveInChar))) {
                    valid++;
                }
            }

            // 当窗口中有效字符以及个数都符合要求时,就要缩小窗口啦.
            while (valid == targetCharacterCounter.size()) {
                // 更新解的数据(因为是解的情况是左闭右开)
                if (right -left < length) {
                    startIndex = left;
                    endIndex = right;
                    length = right -left;
                }


                // 将移出窗口的字符
                char moveOutChar = sourceStr.charAt(left);
                left++;

                if (targetCharacterCounter.containsKey(moveOutChar)) {
                    if (window.get(moveOutChar).equals(targetCharacterCounter.get(moveOutChar))) {
                        valid--;
                    }
                    Integer count = window.getOrDefault(moveOutChar, 0);
                    window.put(moveOutChar, --count);
                }
            }
        }

        if (length == Integer.MAX_VALUE) {
            return  "";
        }

        return sourceStr.substring(startIndex, endIndex);

    }

    private Map<Character, Integer> getTargetCharacterCounter(String targetStr) {
        Map<Character, Integer> targetCharacterCounter = new HashMap<>();
        for (int i = 0; i < targetStr.length(); i++) {
            char character = targetStr.charAt(i);
            Integer thisCharacterCount = targetCharacterCounter.getOrDefault(character, 0);
            targetCharacterCounter.put(character, ++thisCharacterCount);
        }
        return targetCharacterCounter;
    }
}
