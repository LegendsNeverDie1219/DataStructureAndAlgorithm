package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/27 15:21
 */
public class ArrangementOfStrings {
    @Test
    public void testArrangementOfStrings() {
        String s = "eidbaooo";
        String p = "ab";
        boolean result = arrangementOfStrings(s, p);
        System.out.println(result);
    }

    /**
     * 给你两个字符串 s1 和 s2，写⼀个函数来判断 s2 是否包含 s1 的排列（s1 的排列之⼀是 s2 的⼦串）。如
     * 果是，返回 true，否则返回 false。
     * 示例 1：
     * 输⼊：s1 = "ab" s2 = "eidbaooo"
     * 输出：true
     * 解释：s2 包含 s1 的排列之⼀ ("ba").
     *
     * @param sourceStr sourceStr
     * @param targetStr targetStr
     * @return list
     */
    private boolean arrangementOfStrings(String sourceStr, String targetStr) {
        Map<Character, Integer> targetCharCounter = getTargetStrCount(targetStr);
        Map<Character, Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
       // List<Integer> resultList = new ArrayList<>();
        int valid = 0;
        while (right < sourceStr.length()) {
            char moveInChar = sourceStr.charAt(right);
            right++;

            if (targetCharCounter.containsKey(moveInChar)) {
                Integer moveInCharCount = window.getOrDefault(moveInChar,0);
                window.put(moveInChar, ++moveInCharCount);

                if (window.get(moveInChar).equals(targetCharCounter.get(moveInChar))) {
                    valid++;
                }
            }

            // 只要窗口中字符的个数right-left 等于targetStr的字符个数,就可以记录最优解,并缩小窗口了.
            while ((right - left) == targetStr.length()) {
                // 每个字符并且对应的数量也都满足条件
                if (valid == targetCharCounter.size()) {
                   return true;
                }
                char moveOutChar = sourceStr.charAt(left);
                left++;

                if (targetCharCounter.containsKey(moveOutChar)) {
                    if (window.get(moveOutChar).equals(targetCharCounter.get(moveOutChar))) {
                        valid--;
                    }

                    Integer moveOutCharCount = window.getOrDefault(moveOutChar, 0);
                    window.put(moveOutChar, --moveOutCharCount);
                }

            }
        }
        // 未找到符合条件的子串
        return false;
    }

    private Map<Character, Integer> getTargetStrCount(String targetStr) {
        Map<Character, Integer> targetCharCount = new HashMap<>();
        char[] chars = targetStr.toCharArray();
        for (char aChar : chars) {
            Integer count = targetCharCount.getOrDefault(aChar, 0);
            targetCharCount.put(aChar, ++count);
        }
        return targetCharCount;
    }
}

