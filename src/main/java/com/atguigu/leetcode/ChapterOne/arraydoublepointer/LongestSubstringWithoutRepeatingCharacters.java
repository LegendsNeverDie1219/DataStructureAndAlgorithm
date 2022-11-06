package com.atguigu.leetcode.ChapterOne.arraydoublepointer;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/3/26 16:00
 */
public class LongestSubstringWithoutRepeatingCharacters {
    /**
     * 给定⼀个字符串 s，请你找出其中不含有重复字符的最⻓⼦串的⻓度。
     * 示例 1:
     * 输⼊：s = "abcabcbb"
     * 输出：3
     * 解释：因为⽆重复字符的最⻓⼦串是 "abc"，所以其⻓度为 3。
     */
    @Test
    public void testLongestSubstringWithoutRepeatingCharacters() {
        //String str = "abcabcbb";
        String str = "au";
        int length = longestSubstringWithoutRepeatingCharacters(str);
        System.out.println(length);
    }

    private int longestSubstringWithoutRepeatingCharacters(String str) {
        if("".equals(str)) {
            return 0;
        }
        Map<Character, Integer> window = new HashMap<>();
        int left = 0;
        int right = 0;
        int length = 0;
        while (right < str.length()) {
            char moveInChar = str.charAt(right);
            right++;

            Integer count = window.getOrDefault(moveInChar, 0);
            window.put(moveInChar, ++count);

            while (window.get(moveInChar) > 1) {
                char moveOutChar = str.charAt(left);
                left++;
                Integer moveOutCount = window.getOrDefault(moveOutChar, 0);
                window.put(moveOutChar, --moveOutCount);
            }

            // todo 注意更新解的位置.
            if (right - left > length) {
                length = right - left;
            }
        }
        return length;
    }


}
