package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/26 18:30
 */
public class FindOdorWords {
    /**
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的异位词⼦串，返回这些⼦串的起始索引。不考虑答案输出的顺
     * 序。
     * 异位词指由相同字⺟重排列形成的字符串（包括相同的字符串）。
     * 示例 1:
     * 输⼊：s = "cbaebabacd", p = "abc"
     * 输出：[0,6]
     * 解释：
     * 起始索引等于 0 的⼦串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的⼦串是 "bac", 它是 "abc" 的异位词。
     * 示例 2:
     * 输⼊：s = "abab", p = "ab"
     * 输出：[0,1,2]
     * 解释：
     * 起始索引等于 0 的⼦串是 "ab", 它是 "ab" 的异位词。
     * 起始索引等于 1 的⼦串是 "ba", 它是 "ab" 的异位词。
     * 起始索引等于 2 的⼦串是 "ab", 它是 "ab" 的异位词。
     */
    @Test
    public void testFindOdorWords() {
        String s = "cbaebabacd";
        String p = "abc";
        List<Integer> arr = findOdorWords(s, p);
        System.out.println(arr);
    }

    private List<Integer> findOdorWords(String sourceStr, String targetStr) {
        Map<Character, Integer> targetCharCounter = getTargetStrCount(targetStr);
        Map<Character, Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
        List<Integer> resultList = new ArrayList<>();
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
                    resultList.add(left);
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
       // Integer[] integers = resultList.toArray(new Integer[0]);
        return resultList;
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
